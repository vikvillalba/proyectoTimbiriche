package MVCJuegoEnCurso.modelo.interfaces;

import objetosPresentables.TableroPresentable;

/**
 * Interfaz que da acceso a métodos de solo lectura relacionados al tablero de
 * la partida.
 *
 * @author victoria
 */
public interface IModeloTableroLectura {

    /**
     * Obtiene el tablero.
     * traduce de entidad a presentable.
     * @return tablero en formato presentable.
     */
    TableroPresentable getTablero();

}
