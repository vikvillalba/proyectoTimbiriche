package Fachada;

import Entidades.Cuadro;
import Entidades.Jugador;
import Entidades.Linea;
import Entidades.ManejadorTurnos;
import Entidades.Punto;
import Entidades.Tablero;
import Fachada.PartidaFachada;
import Observer.ObservadorInicio;
import Observer.ObservadorTurnos;
import excepciones.PartidaExcepcion;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa una partida de juego. Engloba a todas las clases
 * necesarias para iniciar una partida.
 * Observa al manejador de turnos para notificar cambios en los turnos.
 *
 * @author victoria
 */
public class Partida implements PartidaFachada, ObservadorTurnos {

    private Tablero tablero;
    private ManejadorTurnos turnos;
    private ObservadorTurnos observadorTurnos;
    private ObservadorInicio observadorInicioJuego;
    private List<Jugador> jugadores;

    /**
     * Constructor de partida.
     * @param jugadores jugadores que estarán en la partida
     * @param alto alto del tablero
     * @param ancho ancho del tablero
     * 
     * inicializa un tablero y un manejador de turnos.
     * 
     */
    public Partida(List<Jugador> jugadores, int alto, int ancho) {
        // tablero mock
        this.jugadores = jugadores;
        this.tablero = new Tablero(alto, ancho);
        this.turnos = new ManejadorTurnos(this.jugadores); //jugadores con turnos asignados

        // Registrar la partida como observador de turnos (para notificar al modelo cuando se avance de turno)
        this.turnos.agregarObservadorTurnos(this);
    }

    @Override
    public Punto[] seleccionarPuntos(Punto origen, Punto destino, Jugador jugadorActual) {
        return new Punto[]{origen, destino};
    }

    @Override
    public boolean validarPuntos(Punto origen, Punto destino) throws PartidaExcepcion {
        for (Linea linea : tablero.getLineasExistentes()) {
            // validar que la linea no exista 
            if ((linea.getOrigen().equals(origen) && linea.getDestino().equals(destino)) || (linea.getOrigen().equals(destino) && linea.getDestino().equals(origen))) {
                throw new PartidaExcepcion("Los puntos seleccionados forman una línea que ya existe.");
               
            }

        }
        // validar que el punto origen y destino si sean adyacentes (q no sean diagonales o no tengan nada q ver)
        if (!(Objects.equals(origen.getArriba(), destino)
                || Objects.equals(origen.getAbajo(), destino)
                || Objects.equals(origen.getIzquierda(), destino)
                || Objects.equals(origen.getDerecha(), destino))) {
           throw new PartidaExcepcion("Los puntos seleccionados no se pueden conectar.");
        }
        // si se puede realizar la jugada: checar turnero
        return tablero.unirPuntos(origen, destino, turnos.getJugadorEnTurno());

    }

    @Override
    public void actualizarTurno() {
        turnos.actualizarTurno();
        notificarObservadorTurnos();
    }

    @Override
    public List<Jugador> getJugadores() {
        return turnos.getTurnos();
    }

    @Override
    public Punto getPuntoTablero(int x, int y) {
        return tablero.getPunto(x, y);
    }

    @Override
    public Punto[][] getTablero() {
        return tablero.getPuntos();
    }

    @Override
    public List<Linea> getLineasTablero() {
        return tablero.getLineasExistentes();
    }

    @Override
    public void notificarObservadorTurnos() {
        if (observadorTurnos != null) {
            observadorTurnos.actualizar(jugadores);
        }
    }

    @Override
    public void agregarObservadorTurnos(ObservadorTurnos ob) {
        this.observadorTurnos = ob;
    }

    @Override
    public void notificarObservadorInicioJuego() {
        observadorInicioJuego.iniciarJuego();
    }

    @Override
    public void agregarObservadorInicioJuego(ObservadorInicio ob) {
        observadorInicioJuego = ob;
    }

    @Override
    public List<Cuadro> getCuadrosTablero() {
        return tablero.getCuadrosExistentes();
    }

    @Override
    public void actualizar(List<Jugador> jugadores) {
        notificarObservadorTurnos();
    }

}
