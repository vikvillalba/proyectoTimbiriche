package MVCConfiguracion.modelo;

import DTO.JugadorSolicitanteDTO;
import SolicitudEntity.SolicitudUnirse;

/**
 *
 * @author victoria
 */
public interface IModeloArranqueEscritura {

    //METODOS CU_UNIRSE_PARTIDA
    public JugadorSolicitanteDTO crearJugadorSolicitante(String ip, int puerto);

    public void enviarSolicitud(JugadorSolicitanteDTO jugadorsolicitante);

    public void setEstadoSolicitud(boolean estadoSolicitud);

    public void setSolicitudActual(SolicitudUnirse solicitud);

    public void volverEnviarSolicitud(SolicitudUnirse solicitud);

    public void buscarJugadorEnSalaEspera(JugadorSolicitanteDTO jugadorSolicitante);
}
