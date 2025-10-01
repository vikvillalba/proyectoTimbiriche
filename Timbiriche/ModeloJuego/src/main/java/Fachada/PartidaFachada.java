package Fachada;

import Entidades.Jugador;
import Entidades.Linea;
import Entidades.Punto;
import Observer.ObservableTurnos;
import java.util.List;

/**
 *
 * @author victoria
 */
public interface PartidaFachada extends ObservableTurnos{
    Punto[][] getTablero();
    List<Linea> getLineasTablero();
    Punto[] seleccionarPuntos(Punto origen, Punto destino);
    Punto[] validarPuntos(Punto origen, Punto destino);
    List<Jugador> getJugadores();
    void actualizarTurno();
    Punto getPuntoTablero(int x, int y);
}
