package Observer;

/**
 *
 * @author victoria
 */
public interface Observable {
    void notificarObservadorTurnos();
    void agregarObservadorTurnos(ObservadorTurnos ob);
    void notificarObservadorInicioJuego();
    void agregarObservadorInicioJuego(ObservadorInicio ob);
}
