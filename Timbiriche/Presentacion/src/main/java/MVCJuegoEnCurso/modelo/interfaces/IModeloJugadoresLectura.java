package MVCJuegoEnCurso.modelo.interfaces;

import java.util.List;
import objetosPresentables.JugadorPresentable;

/**
 * Interfaz que da acceso a métodos de solo lectura relacionados a los jugadores
 * de la partida.
 *
 * @author victoria
 */
public interface IModeloJugadoresLectura {

    /**
     * Obtiene los jugadores de la partida.
     * Traduce de entidad a presentable.
     * @return lista de jugadores.
     */
    List<JugadorPresentable> getJugadores();
    
    JugadorPresentable getJugadorSesion();
}
