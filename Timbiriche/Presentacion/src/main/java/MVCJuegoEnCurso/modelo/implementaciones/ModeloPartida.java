package MVCJuegoEnCurso.modelo.implementaciones;


import Entidades.Jugador;
import Entidades.Linea;
import Entidades.Punto;
import Fachada.PartidaFachada;
import MVCJuegoEnCurso.modelo.interfaces.IModeloJugadoresLectura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloTableroLectura;
import MVCJuegoEnCurso.observer.ObservablePartida;
import MVCJuegoEnCurso.observer.ObservadorJugadores;
import MVCJuegoEnCurso.observer.ObservadorTablero;
import Observer.ObservadorTurnos;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import objetosPresentables.JugadorPresentable;
import objetosPresentables.LineaPresentable;
import objetosPresentables.PuntoPresentable;
import objetosPresentables.TableroPresentable;

/**
 *
 * @author victoria
 */
public class ModeloPartida implements IModeloJugadoresLectura, IModeloPartidaEscritura, IModeloTableroLectura, ObservablePartida, ObservadorTurnos {

    private PartidaFachada partida;
    private ObservadorJugadores observadorJugadores;
    private ObservadorTablero observadorTablero;

    public ModeloPartida(PartidaFachada partida) {
        this.partida = partida;
    }

    // traduce jugadores de la partida a presentable
    @Override
    public List<JugadorPresentable> getJugadores() {
        return jugadoresEntidadAPresentable(partida.getJugadores());

    }

    private List<JugadorPresentable> jugadoresEntidadAPresentable(List<Jugador> jugadores) {
        List<JugadorPresentable> jugadoresVista = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            JugadorPresentable jugadorVista = new JugadorPresentable(jugador.getNombre(),
                    null, //jugador.getAvatar(),
                    null, //jugador.getColor(),
                    jugador.getScore(),
                    jugador.isTurno());
            jugadoresVista.add(jugadorVista);
        }
        return jugadoresVista;
    }

    // traduce a entidad y llama al validar de fachada
    @Override
    public boolean unirPuntos(PuntoPresentable[] puntos) {
        Punto[] puntosSeleccionados = partida.validarPuntos(
                partida.getPuntoTablero(puntos[0].getX(), puntos[0].getY()), partida.getPuntoTablero(puntos[1].getX(), puntos[1].getY()));

        if (puntosSeleccionados != null) {
            notificarObservadorTablero();
            return true;
        }
        return false;
    }

    // llama a fachada q actualice los turnos
    @Override
    public void actualizarTurnos() {
        partida.actualizarTurno();
    }

    // traduce a presentable
    @Override
    public TableroPresentable getTablero() {
        Punto[][] tablero = partida.getTablero();

        List<PuntoPresentable> puntosVista = new ArrayList<>();

        for (int i = 0; i < tablero.length; i++) { // recorre filas
            for (int j = 0; j < tablero[i].length; j++) { // recorre elementos de la fila
                PuntoPresentable puntoVista = new PuntoPresentable(i, j);
                puntosVista.add(puntoVista);
            }
        }

        List<Linea> lineasTablero = partida.getLineasTablero();
        List<LineaPresentable> lineasVista = new ArrayList<>();

        for (Linea linea : lineasTablero) {
            PuntoPresentable origen = new PuntoPresentable(linea.getOrigen().getX(), linea.getOrigen().getY());
            PuntoPresentable destino = new PuntoPresentable(linea.getDestino().getX(), linea.getDestino().getY());
            LineaPresentable lineaVista = new LineaPresentable(origen, destino, linea.getColor());
            lineasVista.add(lineaVista);

        }

        // construir tablero presentable (tipo de objeto con el que la vista trabaja)
        TableroPresentable tableroVista = new TableroPresentable(puntosVista, lineasVista, tablero.length, tablero[0].length);
        return tableroVista;
    }

    // actualizar del observador
    @Override
    public void notificarObservadorTablero() {
        observadorTablero.actualizar(getTablero());
    }

    // actualizar del observador
    @Override
    public void notificarObservadorJugadores(List<Jugador> jugadores) {
        List<JugadorPresentable> jugadoresVista = jugadoresEntidadAPresentable(jugadores);
        observadorJugadores.actualizar(jugadoresVista);
    }

    // observa a la partida y cuando se actualizan los turnos notifica 
    // al observador de los jugadores (metodo d arriba)
    @Override
    public void actualizar(List<Jugador> jugadores) {
        notificarObservadorJugadores(jugadores);
    }

    @Override
    public void agregarObservadorTablero(ObservadorTablero ob) {
        this.observadorTablero = ob;
    }

    @Override
    public void agregarObservadorJugadores(ObservadorJugadores ob) {
        this.observadorJugadores = ob;
    }

}
