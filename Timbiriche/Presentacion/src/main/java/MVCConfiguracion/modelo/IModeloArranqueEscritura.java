package MVCConfiguracion.modelo;

import java.util.List;
import objetosPresentables.JugadorConfig;
import objetosPresentables.PartidaPresentable;

/**
 *
 * @author victoria
 */
public interface IModeloArranqueEscritura {
    void solicitarInicioConexion(JugadorConfig jugador);

    void confirmarInicioJuego(JugadorConfig jugador);

    public PartidaPresentable getConfiguracionesPartida();

    void solicitarConfiguraciones();

    public JugadorConfig getSesion();
}
