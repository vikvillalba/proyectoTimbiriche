package MVCJuegoEnCurso.observer;

import objetosPresentables.TableroPresentable;

/**
 * Observador al que el modelo notifica cuando el tablero se actualiza.
 *
 * @author victoria
 */
public interface ObservadorTablero {

    /**
     * recibe notificación de que el tablero tuvo cambios
     *
     * @param tablero tablero actualizado.
     */
    void actualizar(TableroPresentable tablero);
}
