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
            case "RESPUESTA_SOLICITUD":
                System.out.println("no debe de caer aqui se suponeeeee ");
                break;
            case "RESPUESTA_HOST":
                manejarRespuestaHost(paquete);
                break;
            case "SOLICITAR_UNIRSE":
                manejarSolicitudUnirse(paquete);
                break;
            case "RESULTADO_CONSENSO":
                manejarResultadoConsenso(paquete);
                break;
            default:
                System.out.println("[ReceptorUnirsePartida] Evento no manejado: " + tipoEvento);
        }
    }

//    /**
//     * Maneja la respuesta de solicitud del host.
//     */
//    private void manejarRespuestaSolicitud(PaqueteDTO paquete) {
//        try {
//            SolicitudUnirse respuesta = MapperUnirsePartida.mapearSolicitud(paquete.getContenido());
//
//            if (respuesta == null) {
//                System.err.println("ERROR: No se pudo mapear SolicitudUnirse");
//                return;
//            }
//
//            boolean aceptada = respuesta.isSolicitudEstado();
//            String estadoTexto = aceptada ? "ACEPTADA" : "RECHAZADA";
//
//            System.out.println("Respuesta recibida del host: " + estadoTexto);
//
//            unirsePartida.cambiarEstadoSolicitud(respuesta, aceptada);
//
//        } catch (Exception e) {
//            System.err.println("ERROR al manejar respuesta de solicitud: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
    /**
     * Manejar respuesta del host cuando solicita OBTENER_HOST
     */
    private void manejarRespuestaHost(PaqueteDTO paquete) {
        try {
            JugadorConfigDTO jugadorHost = MapperUnirsePartida.mapearHost(paquete.getContenido());

            // Usar actualizar() para que se notifique al modelo
            unirsePartida.modeloArranque.actualizar(jugadorHost);
            unirsePartida.setJugadorHost(jugadorHost);

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

}
