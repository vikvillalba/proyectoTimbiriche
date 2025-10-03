package MVCJuegoEnCurso.observer;

import Entidades.Jugador;
import java.util.List;

/**
 * Interfaz que permite que el modelo sea observable.
 *
 * @author victoria
 */
public interface ObservablePartida {

    /**
     * agrega a un observador de cambios en el tablero.
     *
     * @param ob observador a suscribir
     */
    void agregarObservadorTablero(ObservadorTablero ob);

    /**
     * agrega a un observador de cambios en los jugadores.
     *
     * @param ob observador a suscribir
     */
    void agregarObservadorJugadores(ObservadorJugadores ob);

    /**
     * agrega a un observador para el inicio de juego.
     *
     * @param ob observador a suscribir
     */
    void agregarObservadorInicioJuego(ObservadorInicioPartida ob);

    /**
     * Notifica al observador del tablero que el tablero se actualizó.
     */
    void notificarObservadorTablero();

     /**
     * Notifica al observador de jugadores que el orden de los turnos se actualizó.
     * @param jugadores jugadores con turnos actualizados.
     */
    void notificarObservadorJugadores(List<Jugador> jugadores);

}
