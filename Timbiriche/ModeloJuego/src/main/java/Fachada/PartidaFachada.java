package Fachada;

import Entidades.Cuadro;
import Entidades.Jugador;
import Entidades.Linea;
import Entidades.Punto;
import java.util.List;
import Observer.Observable;

/**
 *
 * @author victoria
 */
public interface PartidaFachada extends Observable{
    Punto[][] getTablero();
    List<Linea> getLineasTablero();
    List<Cuadro> getCuadrosTablero();
    Punto[] seleccionarPuntos(Punto origen, Punto destino, Jugador jugadorActual);
    Punto[] validarPuntos(Punto origen, Punto destino);
    List<Jugador> getJugadores();
    void actualizarTurno();
    Punto getPuntoTablero(int x, int y);
    
   
}
