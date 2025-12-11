package ModeloUnirsePartida;

import DTO.JugadorConfigDTO;
import SolicitudEntity.SolicitudUnirse;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 * Receptor que maneja las respuestas de solicitud en el lado del SOLICITANTE. Este receptor solo recibe eventos de tipo RESPUESTA_SOLICITUD.
 *
 * @author Jack Murrieta
 */
public class ReceptorUnirsePartida implements IReceptor {

    private UnirsePartida unirsePartida;

    public ReceptorUnirsePartida(UnirsePartida unirsePartida) {
        this.unirsePartida = unirsePartida;
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        String tipoEvento = paquete.getTipoEvento();

        System.out.println("[ReceptorUnirsePartida] Paquete recibido: " + tipoEvento);

        switch (tipoEvento) {
            //RESPUESTA DEL EVENTBUS SI ENCONTRO UN JUGADOR EN SALA_ESPERA
            case "RESPUESTA_JUGADOR":
                manejarRespuestaJugador(paquete);
                break;
            case "SOLICITAR_UNIRSE":
                manejarSolicitudUnirse(paquete);
                break;
            case "RESULTADO_CONSENSO":
                // para el jugador a unirse
                manejarResultadoConsenso(paquete);
                break;
            case "CONSENSO_FINALIZADO":
                //para jugadores en sala 
                manejarConsensoFinalizado(paquete);
                break;
            default:
                System.out.println("[ReceptorUnirsePartida] Evento no manejado: " + tipoEvento);
        }
    }

    /**
     * Manejar respuesta del host cuando solicita OBTENER_HOST.
     * Este método es llamado SOLO en CLIENTES que buscan partidas existentes.
     * El HOST nunca ejecuta esta lógica.
     *
     * Flujo:
     * 1. Recibe JugadorConfigDTO del host encontrado
     * 2. Notifica a ModeloArranque (que convierte DTO→Presentable)
     * 3. ModeloArranque notifica a FrmMenuInicio
     * 4. FrmMenuInicio muestra diálogo para unirse
     */
    private void manejarRespuestaJugador(PaqueteDTO paquete) {
        try {
            JugadorConfigDTO jugadorEnSala = MapperUnirsePartida.mapearJugadorEnSala(paquete.getContenido());

            unirsePartida.notificarJugadorEncontrado(jugadorEnSala);

        } catch (Exception e) {
            System.err.println("ERROR al manejar RESPUESTA_HOST: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Logica para el jugador a unirse a partida
    /**
     * Maneja la solicitud de unirse recibida desde un solicitante.
     *
     * @param paquete Paquete con la solicitud
     */
    private void manejarSolicitudUnirse(PaqueteDTO paquete) {
        try {
            // Extraer la solicitud del paquete usando el mapper
            SolicitudUnirse solicitud = MapperUnirsePartida.mapearSolicitud(paquete.getContenido());

            if (solicitud == null) {
                System.err.println("ERROR: No se pudo mapear la solicitud recibida");
                return;
            }

            // Guardar la solicitud recibida sin cambiar su estado
            // El estado lo cambiará el HOST cuando acepte o rechace
            if (unirsePartida instanceof UnirsePartida) {
                ((UnirsePartida) unirsePartida).setSolicitudActual(solicitud);
            }

            System.out.println("Solicitud recibida de: "
                    + solicitud.getJugadorSolicitante().getIp() + ":"
                    + solicitud.getJugadorSolicitante().getPuerto());

            // Notificar al modelo que a su vez notifica a la vista HOST
            // para que se muestre el diálogo de aceptar/rechazar
            unirsePartida.cambiarEstadoSolicitud(solicitud, solicitud.isSolicitudEstado());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el resultado del consenso enviado por el EventBus. Este método es llamado solo en el SOLICITANTE cuando todos los jugadores votaron.
     *
     * @param paquete Paquete con el resultado del consenso
     */
    private void manejarResultadoConsenso(PaqueteDTO paquete) {
        try {
            SolicitudUnirse resultado = MapperUnirsePartida.mapearSolicitud(paquete.getContenido());

            if (resultado == null) {
                System.err.println("[ReceptorUnirsePartida] ERROR: No se pudo mapear resultado de consenso");
                return;
            }

            boolean aceptada = resultado.isSolicitudEstado();
            String estadoTexto = aceptada ? "ACEPTADA POR CONSENSO" : "RECHAZADA POR CONSENSO";

            System.out.println("[ReceptorUnirsePartida] Resultado del consenso recibido: " + estadoTexto);

            if (!aceptada) {
                System.out.println("[ReceptorUnirsePartida] Tipo de rechazo: " + resultado.getTipoRechazo());
            }

            // Notificar al modelo (y a su vez a la vista del solicitante)
            // para mostrar el resultado final
            unirsePartida.cambiarEstadoSolicitud(resultado, aceptada);

        } catch (Exception e) {
            System.err.println("[ReceptorUnirsePartida] ERROR al manejar resultado de consenso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Maneja la notificación de CONSENSO_FINALIZADO enviada por el EventBus. Este método es llamado en TODOS los jugadores en sala de espera cuando el consenso termina.
     *
     * @param paquete Paquete con los datos del consenso finalizado
     */
    private void manejarConsensoFinalizado(PaqueteDTO paquete) {
        try {
            // El contenido es un Map con: consensoAceptado, keySolicitud, tipoRechazo
            java.util.Map<String, Object> contenido = (java.util.Map<String, Object>) paquete.getContenido();

            boolean aceptado = (Boolean) contenido.get("consensoAceptado");
            String keySolicitud = (String) contenido.get("keySolicitud");
            String tipoRechazo = (String) contenido.get("tipoRechazo");

            String estadoTexto = aceptado ? "ACEPTADO" : "RECHAZADO";
            System.out.println("[ReceptorUnirsePartida] CONSENSO_FINALIZADO recibido - Estado: " + estadoTexto);
            System.out.println("[ReceptorUnirsePartida] Key solicitud: " + keySolicitud);

            if (!aceptado) {
                System.out.println("[ReceptorUnirsePartida] Tipo de rechazo: " + tipoRechazo);
            }

            // Notificar a la vista (FrmSalaEspera) para que cierre el diálogo y muestre el mensaje
            unirsePartida.notificarConsensoFinalizado(aceptado, tipoRechazo);

        } catch (Exception e) {
            System.err.println("[ReceptorUnirsePartida] ERROR al manejar CONSENSO_FINALIZADO: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
