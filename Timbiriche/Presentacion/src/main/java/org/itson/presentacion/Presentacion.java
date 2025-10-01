package org.itson.presentacion;

import Entidades.Partida;
import Fachada.PartidaFachada;
import MVCJuegoEnCurso.controlador.ControladorPartida;
import MVCJuegoEnCurso.modelo.implementaciones.ModeloPartida;
import MVCJuegoEnCurso.modelo.interfaces.IModeloJugadoresLectura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloTableroLectura;
import MVCJuegoEnCurso.vista.FrmPartida;

/**
 *
 * @author victoria
 */
public class Presentacion {

    public static void main(String[] args) {
        PartidaFachada partidaFachada = new Partida();
        ModeloPartida modeloPartida = new ModeloPartida(partidaFachada);

        IModeloJugadoresLectura modeloJugadoresLectura = modeloPartida;
        IModeloTableroLectura modeloTableroLectura = modeloPartida;
        IModeloPartidaEscritura modeloPartidaEscritura = modeloPartida;

        ControladorPartida controlador = new ControladorPartida(modeloPartidaEscritura);
        FrmPartida partida = new FrmPartida(modeloJugadoresLectura, modeloTableroLectura, controlador);

        partidaFachada.agregarObservadorTurnos(modeloPartida);
        modeloPartida.agregarObservadorJugadores(partida.getObservadorJugadores());
        modeloPartida.agregarObservadorTablero(partida.getObservadorTablero());

        partida.setVisible(true);
    }
}
