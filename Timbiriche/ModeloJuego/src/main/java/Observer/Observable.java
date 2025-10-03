package Observer;

/**
 * Interfaz que permite que la partida pueda ser observada.
 *
 * @author victoria
 */
public interface Observable {

    /**
     * Agrega un observador para el orden de los turnos
     *
     * @param ob observador a suscribir.
     */
    void agregarObservadorTurnos(ObservadorTurnos ob);

    /**
     * notifica al observador de turnos que hay un nuevo jugador en turno.
     */
    void notificarObservadorTurnos();

    /**
     * Agrega un observador para el inicio del juego
     *
     * @param ob observador a suscribir.
     */
    void agregarObservadorInicioJuego(ObservadorInicio ob);

    /**
     * Notifica al observador cuando el juego inicia.
     */
    void notificarObservadorInicioJuego();
}
