package ModeloUnirsePartida;

import SolicitudEntity.SolicitudUnirse;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 * Receptor que maneja las respuestas de solicitud en el lado del SOLICITANTE. Este receptor solo recibe eventos de tipo RESPUESTA_SOLICITUD.
 *
 * @author Jack Murrieta
 */
public class ReceptorSolicitudCliente implements IReceptor {

    private IUnirsePartida unirsePartida;

    public ReceptorSolicitudCliente(IUnirsePartida unirsePartida) {
        this.unirsePartida = unirsePartida;
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        String tipoEvento = paquete.getTipoEvento();

        System.out.println("ReceptorSolicitudCliente - Paquete recibido: " + tipoEvento);

        if ("RESPUESTA_SOLICITUD".equals(tipoEvento)) {
            manejarRespuestaSolicitud(paquete);
        } else {
            System.out.println("Evento no manejado por ReceptorSolicitudCliente: " + tipoEvento);
        }
    }

    /**
     * Maneja la respuesta de la solicitud recibida desde el host.
     *
     * @param paquete Paquete con la respuesta
     */
    private void manejarRespuestaSolicitud(PaqueteDTO paquete) {
        try {
            // Extraer la respuesta del paquete
            SolicitudUnirse respuesta = (SolicitudUnirse) paquete.getContenido();

            boolean aceptada = respuesta.isSolicitudEstado();
            String estadoTexto = aceptada ? "ACEPTADA ✓" : "RECHAZADA ✗";

            System.out.println("✓ Respuesta recibida del host: " + estadoTexto);

            // Actualizar la solicitud en el modelo
            unirsePartida.cambiarEstadoSolicitud(respuesta, aceptada);

            if (aceptada) {
                System.out.println("✓ ¡Tu solicitud fue aceptada! Entrando a la sala de espera...");
            } else {
                System.out.println("✗ Tu solicitud fue rechazada por el host.");
            }

        } catch (ClassCastException e) {
            System.err.println("ERROR: El contenido del paquete no es una SolicitudUnirse");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERROR al manejar respuesta de solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
