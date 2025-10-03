package MVCJuegoEnCurso.observer;

import java.util.List;
import objetosPresentables.JugadorPresentable;

/**
 * Observador al que el modelo notifica cuando el orden de los turnos se
 * actualiza.
 *
 * @author victoria
 */
public interface ObservadorJugadores {

    /**
     * recibe notificación de que hubo cambios en los turnos de los jugadores.
     * @param jugadores lista de jugadores con los turnos actualizados.
     */
    void actualizar(List<JugadorPresentable> jugadores);
}
