package MVCConfiguracion.controlador;

import DTO.JugadorSolicitanteDTO;
import MVCConfiguracion.modelo.IModeloArranqueEscritura;
import MVCConfiguracion.observer.ObservadorEventoInicio;
import MVCJuegoEnCurso.controlador.ControladorPartida;
import SolicitudEntity.SolicitudUnirse;
import java.util.List;
import objetosPresentables.JugadorConfig;
import objetosPresentables.PartidaPresentable;

/**
 * Controlador que maneja las operaciones de arranque de la partida y las solicitudes para unirse a una partida.
 *
 * @author victoria
 */
public class ControladorArranque implements ObservadorEventoInicio, ControladorUnisePartida {

    private ControladorPartida controladorPartida;
    private JugadorConfig sesion;
    private IModeloArranqueEscritura modelo;

    public ControladorArranque(ControladorPartida controladorPartida, JugadorConfig sesion, IModeloArranqueEscritura modelo) {
        this.controladorPartida = controladorPartida;
        this.sesion = sesion;
        this.modelo = modelo;
    }

    public void solicitarInicioJuego(JugadorConfig jugador) {

    }

    public void iniciarConexion(List<JugadorConfig> jugadores, int altoTablero, int anchoTablero, JugadorConfig sesion) {
    }

    public void confirmarInicioJuego(JugadorConfig jugador) {
    }

    @Override
    public void iniciar(PartidaPresentable partida) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // ========== IMPLEMENTACIÓN DE ControladorUnisePartida ==========
    /**
     * Envía una solicitud para unirse a una partida existente.
     *
     * @param ip IP del jugador que desea unirse
     * @param puerto Puerto del jugador que desea unirse
     */
    @Override
    public void enviarSolicitud(String ip, int puerto) {
        // Validar parámetros
        if (ip == null || ip.trim().isEmpty()) {
            throw new IllegalArgumentException("La IP no puede estar vacía");
        }
        if (puerto == 0) {
            throw new IllegalArgumentException("El puerto no puede estar vacío");
        }

        // Crear el jugador solicitante
        JugadorSolicitanteDTO jugadorSolicitante = modelo.crearJugadorSolicitante(ip, puerto);

        // Enviar la solicitud
        modelo.enviarSolicitud(jugadorSolicitante);
    }

    /**
     * Acepta o rechaza una solicitud para unirse a la partida. Este método es ejecutado por el host.
     *
     * @param esAceptada true para aceptar, false para rechazar
     * @return true si la operación fue exitosa
     */
    @Override
    public void aceptarSolicitud(boolean esAceptada) {
        //setea el estado de la solicitud se la manda a modelo
        modelo.setEstadoSolicitud(esAceptada);

    }

}
