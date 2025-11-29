package MVCConfiguracion.modelo;

import java.util.List;
import objetosPresentables.JugadorConfig;

/**
 *
 * @author victoria
 */
public interface IModeloArranqueEscritura {
    void iniciarConexion(List<JugadorConfig> jugadores, int altoTablero, int anchoTablero, JugadorConfig sesion);
    void solicitarInicioConexion(JugadorConfig jugador);
    void confirmarInicioJuego(JugadorConfig jugador);
}
