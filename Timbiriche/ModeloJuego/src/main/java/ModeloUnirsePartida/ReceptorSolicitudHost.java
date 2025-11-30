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

        // Extraer la solicitud del paquete
        SolicitudUnirse solicitud = (SolicitudUnirse) paquete.getContenido();

        System.out.println("Solicitud recibida de: "
                + solicitud.getJugadorSolicitante().getIp() + ":"
                + solicitud.getJugadorSolicitante().getPuerto());

        unirsePartida.cambiarEstadoSolicitud(solicitud, solicitud.isSolicitudEstado());

    }

}
