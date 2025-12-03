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
public class ReceptorSolicitudCliente implements IReceptor {

    private UnirsePartida unirsePartida;

    public ReceptorSolicitudCliente(UnirsePartida unirsePartida) {
        this.unirsePartida = unirsePartida;
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        String tipoEvento = paquete.getTipoEvento();

        System.out.println("ReceptorSolicitudCliente - Paquete recibido: " + tipoEvento);

        switch (tipoEvento) {
            case "RESPUESTA_SOLICITUD":
                manejarRespuestaSolicitud(paquete);
                break;
            case "RESPUESTA_HOST":
                manejarRespuestaHost(paquete);
                break;
            default:
                System.out.println("Evento no manejado por ReceptorSolicitudCliente: " + tipoEvento);
        }
    }

    /**
     * Maneja la respuesta de solicitud del host.
     */
    private void manejarRespuestaSolicitud(PaqueteDTO paquete) {
        try {
            SolicitudUnirse respuesta = MapperUnirsePartida.mapearSolicitud(paquete.getContenido());

            if (respuesta == null) {
                System.err.println("ERROR: No se pudo mapear SolicitudUnirse");
                return;
            }

            boolean aceptada = respuesta.isSolicitudEstado();
            String estadoTexto = aceptada ? "ACEPTADA" : "RECHAZADA";

            System.out.println("Respuesta recibida del host: " + estadoTexto);

            unirsePartida.cambiarEstadoSolicitud(respuesta, aceptada);

        } catch (Exception e) {
            System.err.println("ERROR al manejar respuesta de solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }

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

}
