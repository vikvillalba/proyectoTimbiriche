package MVCConfiguracion.observer;

import java.util.List;
import objetosPresentables.JugadorConfig;

/**
 *
 * @author victoria
 */
public interface ObservableInicioPartida {
    void agregarObservadorInicioPartida(ObservableInicioPartida ob);
    void notificarInicio(List<JugadorConfig> jugadores, int altoTablero, int anchoTablero, JugadorConfig sesion);
}
