package Entidades;

import Fachada.PartidaFachada;
import Observer.ObservadorInicio;
import Observer.ObservadorTurnos;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author victoria
 */
public class Partida implements PartidaFachada, ObservadorTurnos{
    
    
    
    private Tablero tablero;
    private ManejadorTurnos turnos;
    private ObservadorTurnos observadorTurnos;
    private ObservadorInicio observadorInicioJuego;
    private List<Jugador> jugadores;

    public Partida(List<Jugador> jugadores, int alto, int ancho) {
        // tablero mock
        this.jugadores = jugadores;
        this.tablero = new Tablero(alto, ancho);
        this.turnos = new ManejadorTurnos(this.jugadores); //jugadores con turnos asignados
        
        // Registrar la partida como observador de turnos
        this.turnos.agregarObservadorTurnos(this);
    }

    @Override
    public Punto[] seleccionarPuntos(Punto origen, Punto destino, Jugador jugadorActual) {
        tablero.unirPuntos(origen, destino, jugadorActual); // <-- pasa jugador
        return new Punto[]{origen, destino};
    }

    @Override
    public Punto[] validarPuntos(Punto origen, Punto destino) {
        for (Linea linea : tablero.getLineasExistentes()) {
            // validar que la linea no exista 
            if ((linea.getOrigen().equals(origen) && linea.getDestino().equals(destino)) || (linea.getOrigen().equals(destino) && linea.getDestino().equals(origen))) {
                System.out.println("la linea ya existe"); // TODO: lanzar excepcion
                return null;
            }

        }
        // validar que el punto origen y destino si sean adyacentes (q no sean diagonales o no tengan nada q ver)
        if (!(Objects.equals(origen.getArriba(), destino)
                || Objects.equals(origen.getAbajo(), destino)
                || Objects.equals(origen.getIzquierda(), destino)
                || Objects.equals(origen.getDerecha(), destino))) {
            System.out.println("Los puntos no se pueden conectar"); // TODO: lanzar excepcion
            return null;
        }
        // si se puede realizar la jugada:
        return seleccionarPuntos(origen, destino, turnos.getJugadorEnTurno());

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
