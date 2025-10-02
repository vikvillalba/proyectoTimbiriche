package MVCJuegoEnCurso.observer;

import Entidades.Jugador;
import java.util.List;

/**
 *
 * @author victoria
 */
public interface ObservablePartida {
    void notificarObservadorTablero();
    void notificarObservadorJugadores(List<Jugador> jugadores);
    void agregarObservadorTablero(ObservadorTablero ob); 
    void agregarObservadorJugadores(ObservadorJugadores ob); 
    void agregarObservadorInicioJuego(ObservadorInicioPartida ob);
}
