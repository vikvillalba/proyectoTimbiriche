package MVCJuegoEnCurso.modelo.interfaces;

import objetosPresentables.PuntoPresentable;


/**
 *
 * @author victoria
 */
public interface IModeloPartidaEscritura {
    boolean unirPuntos(PuntoPresentable[] puntos);
    void actualizarTurnos();
}
