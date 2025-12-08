package MVCConfiguracion.observer;

import java.util.List;
import objetosPresentables.JugadorConfig;

/**
 *
 * @author victoria
 */
public interface ObservadorInicioPartida {
    void iniciar(List<JugadorConfig> jugadores, int altoTablero, int anchoTablero, JugadorConfig sesion);
}
