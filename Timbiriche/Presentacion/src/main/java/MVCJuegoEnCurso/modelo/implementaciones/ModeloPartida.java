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
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final Map<String, Color> COLORES = new HashMap<>();
    private static final Map<String, Image> AVATARES = new HashMap<>();

    public ModeloPartida(PartidaFachada partida) {
        this.partida = partida;
    }

    // traduce jugadores de la partida a presentable
    @Override
    public List<JugadorPresentable> getJugadores() {
        return jugadoresEntidadAPresentable(partida.getJugadores());

    }
    
    static {
        // Mapa con los nombres que se usan
        COLORES.put("rojo_pastel", Color.RED);
        COLORES.put("azul_pastel", Color.BLUE);
        COLORES.put("verde_pastel", Color.GREEN);
        COLORES.put("amarillo_pastel", Color.YELLOW);
        COLORES.put("magenta", Color.MAGENTA);
        COLORES.put("naranja_pastel", Color.ORANGE);
        COLORES.put("rosa_pastel", Color.PINK);
        COLORES.put("azul_marino", Color.BLUE);
    }

    private List<JugadorPresentable> jugadoresEntidadAPresentable(List<Jugador> jugadores) {
        List<JugadorPresentable> jugadoresVista = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            
            JugadorPresentable jugadorVista = new JugadorPresentable(jugador.getNombre(),
                    null,
                    null,
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
            
            LineaPresentable lineaVista = new LineaPresentable(origen, destino,COLORES.get(linea.getColor())); //Solo toma el color del  primero
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
