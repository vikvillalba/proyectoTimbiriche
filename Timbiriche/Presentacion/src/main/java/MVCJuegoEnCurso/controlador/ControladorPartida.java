package MVCJuegoEnCurso.controlador;

import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import objetosPresentables.PuntoPresentable;

/**
 *
 * @author victoria
 */
public class ControladorPartida {

    private IModeloPartidaEscritura modelo;
    private PuntoPresentable[] puntosSeleccionados = new PuntoPresentable[2];

    public ControladorPartida(IModeloPartidaEscritura modelo) {
        this.modelo = modelo;
    }

    public boolean agregarPuntoSeleccionado(PuntoPresentable punto) {
        if (puntosSeleccionados[0] == null) {
            puntosSeleccionados[0] = punto;
        } else {
            puntosSeleccionados[1] = punto;
            boolean realizado = modelo.unirPuntos(puntosSeleccionados); // ya se seleccionaron los 2 puntos lol
            puntosSeleccionados[0] = null;
            puntosSeleccionados[1] = null;
            actualizarTurno();
            return realizado;
            
        }
        return false;
    }

    public void actualizarTurno() {
        modelo.actualizarTurnos();
    }
}
