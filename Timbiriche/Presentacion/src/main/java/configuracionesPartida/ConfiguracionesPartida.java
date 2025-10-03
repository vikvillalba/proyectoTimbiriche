package configuracionesPartida;

import Entidades.Jugador;
import Fachada.Partida;
import Fachada.PartidaFachada;
import MVCJuegoEnCurso.controlador.ControladorPartida;
import MVCJuegoEnCurso.modelo.implementaciones.ModeloPartida;
import MVCJuegoEnCurso.modelo.interfaces.IModeloJugadoresLectura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloTableroLectura;
import MVCJuegoEnCurso.vista.FrmPartida;
import java.util.List;

/**
 *
 * @author victoria
 */
public class ConfiguracionesPartida {
    public static void iniciarPartida(List<Jugador> jugadores, int alto, int ancho){

        PartidaFachada partida = new Partida(jugadores, alto, ancho);
        ModeloPartida modeloPartida = new ModeloPartida(partida);

        IModeloJugadoresLectura modeloJugadoresLectura = modeloPartida;
        IModeloTableroLectura modeloTableroLectura = modeloPartida;
        IModeloPartidaEscritura modeloPartidaEscritura = modeloPartida;

        ControladorPartida controlador = new ControladorPartida(modeloPartidaEscritura);
        FrmPartida frmPartida = new FrmPartida(modeloJugadoresLectura, modeloTableroLectura, controlador);

        partida.agregarObservadorTurnos(modeloPartida);
        partida.agregarObservadorInicioJuego(modeloPartida);
        partida.agregarObservadorInicioJuego(modeloPartida);
        modeloPartida.agregarObservadorJugadores(frmPartida.getObservadorJugadores());
        modeloPartida.agregarObservadorTablero(frmPartida.getObservadorTablero());
        modeloPartida.agregarObservadorInicioJuego(frmPartida);
        
        partida.notificarObservadorInicioJuego();

    }
}
