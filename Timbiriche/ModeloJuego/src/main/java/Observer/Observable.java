package Observer;

/**
 * Interfaz que permite que la partida pueda ser observada.
 *
 * @author victoria
 */
public interface Observable {

    // Jugadores
    void agregarObservadorJugadores(ObservadorJugadores ob);
    void notificarObservadorJugadores();

    // Inicio de partida
    void agregarObservadorInicioJuego(ObservadorInicio ob);
    void notificarObservadorInicioJuego();
}
