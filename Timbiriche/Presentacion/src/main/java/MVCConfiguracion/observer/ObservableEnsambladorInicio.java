package MVCConfiguracion.observer;

import Entidades.Jugador;
import java.util.List;

/**
 *
 * @author victoria
 */
public interface ObservableEnsambladorInicio {
    void agregarObservadorEnsamblador(ObservadorEnsamblador ob);
    void notificarEnsamblador(List<Jugador> jugadores, int altoTablero, int anchoTablero, Jugador sesion);
}
