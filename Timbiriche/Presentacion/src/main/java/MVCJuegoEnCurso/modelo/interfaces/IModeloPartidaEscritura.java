package MVCJuegoEnCurso.modelo.interfaces;

import excepciones.JugadaException;
import objetosPresentables.PuntoPresentable;

/**
 * Interfaz que da acceso a métodos de solo escritura relacionados a una
 * partida.
 *
 * @author victoria
 */
public interface IModeloPartidaEscritura {

    /**
     * Une dos puntos del tablero. llama a la fachada para comunicarse con el
     * modelo de juego y que valide la jugada.
     *
     * @param puntos arreglo con los puntos seleccionados.
     * @return true si los puntos se unieron, false en caso contrario.
     */
    boolean unirPuntos(PuntoPresentable[] puntos) throws JugadaException;

    /**
     * Llama a la fachada para que actualice al jugador en turno.
     */
    void actualizarTurnos();
}
