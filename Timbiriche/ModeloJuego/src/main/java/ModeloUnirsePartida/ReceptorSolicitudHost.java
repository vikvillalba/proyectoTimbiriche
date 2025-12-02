package ModeloUnirsePartida;

import SolicitudEntity.SolicitudUnirse;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 * Receptor que maneja las solicitudes de unirse a la partida en el lado del HOST. Este receptor solo recibe eventos de tipo SOLICITAR_UNIRSE.
 *
 * @author Jack Murrieta
 */
public class ReceptorSolicitudHost implements IReceptor {

    private IUnirsePartida unirsePartida;

    public ReceptorSolicitudHost(IUnirsePartida unirsePartida) {
        this.unirsePartida = unirsePartida;
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        String tipoEvento = paquete.getTipoEvento();

        System.out.println(" ReceptorSolicitudHost - Paquete recibido: " + tipoEvento);

        if ("SOLICITAR_UNIRSE".equals(tipoEvento)) {
            manejarSolicitudUnirse(paquete);
        } else {
            System.out.println("Evento no manejado por ReceptorSolicitudHost: " + tipoEvento);
        }
    }

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

            System.out.println("✓ Solicitud recibida de: "
                + solicitud.getJugadorSolicitante().getIp() + ":"
                + solicitud.getJugadorSolicitante().getPuerto());

            // Notificar al modelo (que a su vez notificará a la vista HOST)
            // para que se muestre el diálogo de aceptar/rechazar
            unirsePartida.cambiarEstadoSolicitud(solicitud, solicitud.isSolicitudEstado());

        } catch (Exception e) {
            System.err.println("ERROR al manejar solicitud de unirse: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
