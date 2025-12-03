package MVCConfiguracion.observer;

import Entidades.Jugador;
import java.util.List;

/**
 *
 * @author victoria
 */
public interface ObservadorEnsamblador {
    void ensamblarPartida(List<Jugador> jugadores, int altoTablero, int anchoTablero, Jugador sesion);
}
