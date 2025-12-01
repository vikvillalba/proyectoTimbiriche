package MVCJuegoEnCurso.controlador;

import MVCJuegoEnCurso.modelo.interfaces.IModeloAbandonarEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloMensajeEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import excepciones.JugadaException;
import objetosPresentables.PuntoPresentable;

/**
 * Controlador que maneja los eventos durante una partida en curso
 *
 * @author victoria
 */
public class ControladorPartida {

    private IModeloPartidaEscritura modelo;
    private IModeloMensajeEscritura modeloMensaje;
    private IModeloAbandonarEscritura modeloAbandonar;
    private PuntoPresentable[] puntosSeleccionados = new PuntoPresentable[2]; // puntos que el jugador selecciona

    public ControladorPartida(IModeloPartidaEscritura modelo, IModeloMensajeEscritura modeloMensaje, IModeloAbandonarEscritura modeloAbandonar) {
        this.modelo = modelo;
        this.modeloMensaje = modeloMensaje;
        this.modeloAbandonar = modeloAbandonar;
    }

    /**
     * Guarda el punto que el jugador seleccionó. cuando el jugador selecciona 2
     * puntos, se llama al modelo para que una dichos puntos.
     *
     * @param punto punto seleccionado desde la vista.
     * @return si los puntos pueden unirse o no.
     */
    public boolean agregarPuntoSeleccionado(PuntoPresentable punto) throws JugadaException {
        if (puntosSeleccionados[0] == null) {
            puntosSeleccionados[0] = punto;
        } else {
            try {
                puntosSeleccionados[1] = punto;
                boolean realizado = modelo.unirPuntos(puntosSeleccionados); // ya se seleccionaron los 2 puntos

            } catch (JugadaException ex) {
                puntosSeleccionados[0] = null;
                puntosSeleccionados[1] = null;
//                actualizarTurno();

                throw ex;

            }
            puntosSeleccionados[0] = null;
            puntosSeleccionados[1] = null;
//            actualizarTurno();

        }
        return false;
    }
    
    public void mostrarMensaje(){
        modeloMensaje.mostrarMensaje();
    }
    
    public void abandonarPartida(){
        modeloAbandonar.abandonarPartida();
    }

    /**
     * Llamada al modelo para que actualice los turnos de la partida.
     */
//    public void actualizarTurno() {
//        modelo.actualizarTurnos();
//        System.out.println("Estuve en el controlador");
//    }
}
