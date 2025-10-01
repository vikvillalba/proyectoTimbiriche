
package MVCJuegoEnCurso.observer;


import java.util.List;
import objetosPresentables.JugadorPresentable;

/**
 *
 * @author victoria
 */
public interface ObservadorJugadores {
    void actualizar(List<JugadorPresentable> jugadores);
}
