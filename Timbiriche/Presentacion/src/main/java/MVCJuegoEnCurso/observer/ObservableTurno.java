package MVCJuegoEnCurso.observer;

import objetosPresentables.JugadorPresentable;

/**
 *
 * @author victoria
 */
public interface ObservableTurno {
    
    void agregarObservadorturno(ObservadorTurno ob);
    void notificar();
        
}
