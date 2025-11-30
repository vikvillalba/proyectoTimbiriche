package MVCConfiguracion.controlador;

import MVCConfiguracion.modelo.IModeloArranqueEscritura;
import MVCConfiguracion.observer.ObservadorEventoInicio;
import MVCJuegoEnCurso.controlador.ControladorPartida;
import java.util.List;
import objetosPresentables.JugadorConfig;
import objetosPresentables.PartidaPresentable;
import org.itson.dto.JugadorDTO;

/**
 *
 * @author victoria
 */
public class ControladorArranque implements ObservadorEventoInicio {

    private ControladorPartida controladorPartida;
    private IModeloArranqueEscritura modelo;

    public ControladorArranque(ControladorPartida controladorPartida, IModeloArranqueEscritura modelo) {
        this.controladorPartida = controladorPartida;
        this.modelo = modelo;
    }

    /**
     * Solicita a los demás jugadores conectados iniciar el juego.
     */
    public void solicitarInicioJuego(JugadorConfig jugador) {
        // llamada al modelo
        modelo.solicitarInicioConexion(jugador);
    }

    public void iniciarConexion(List<JugadorConfig> jugadores, int altoTablero, int anchoTablero, JugadorConfig sesion) {
    }

    /**
     * El jugador de sesión acepta la solicitud de inicio de juego.
     */
    public void confirmarInicioJuego(JugadorConfig jugador) {
        modelo.confirmarInicioJuego(jugador);
    }

    public void obtenerConfiguraciones() {
        modelo.solicitarConfiguraciones();
    }

    @Override
    public void iniciar(PartidaPresentable partida) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
