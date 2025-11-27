package Fachada;

import Entidades.Cuadro;
import Entidades.Jugador;
import Entidades.Linea;
import Entidades.Punto;
import java.util.List;
import Observer.Observable;
import excepciones.PartidaExcepcion;
import org.itson.dto.PaqueteDTO;

/**
 * Interfaz fachada para acceder a los métodos de la partida.
 *
 * @author victoria
 */
public interface PartidaFachada extends Observable {

    /**
     * Obtiene el tablero como una matriz de puntos.
     *
     * @return tablero representado por una matriz de puntos.
     */
    Punto[][] getTablero();

    /**
     * obtiene las lineas existentes del tablero.
     *
     * @return lista de líneas que existen en el tablero
     */
    List<Linea> getLineasTablero();

    /**
     * obtiene los cuadros posibles que se pueden llenar del tablero
     *
     * @return lista de cuadros del tablero.
     */
    List<Cuadro> getCuadrosTablero();

    /**
     * Valida que dos puntos sean adyacentes y puedan formar una línea. si los
     * puntos son válidos se llama a seleccionarPuntos()
     *
     * @param origen punto origen
     * @param destino punto destino
     * @return true si los puntos son adyacentes y se pueden unir, false en caso
     * contrario.
     */
    boolean validarPuntos(Punto origen, Punto destino) throws PartidaExcepcion;

    /**
     * Selecciona dos punros del tablero que forman una línea.
     *
     * @param origen punto origen
     * @param destino punto destino
     * @param jugadorActual jugador que seleccionó los puntos
     * @return arreglo de puntos seleccionados
     */
    Punto[] seleccionarPuntos(Punto origen, Punto destino, Jugador jugadorActual);

    /**
     * Obtiene los jugadores ordenados según los turnos repartidos.
     *
     * @return lista de los jugadores ordenada por turnos.
     */
    List<Jugador> getJugadores();

    /**
     * Actualiza el turno de los jugadores.
     * llama al manejador de turnos para que continúe con el jugador que sigue.
     */
    void actualizarTurno();
    
    /** Obtiene un punto del tablero.
     * @param x posicion x en la matriz del tablero
     * @param y posicion y en la matriz del tablero
     * @return punto correspondiente 
     */
    Punto getPuntoTablero(int x, int y);

    /**
     * Obtiene al jugador que está actualmente jugando en la pantalla.
     * @return 
     */
    Jugador getJugadorSesion();
    
    /**
     * Establece al jugador que está actualmente jugando en la pantalla. 
     * @param jugadorSesion
     */
    void setJugadorSesion(Jugador jugadorSesion);
     
    /**
     * Recibe el evento de NuevaLinea con los puntos.
     * Crea una línea para dibujarse en el tablero.
     * @param paquete
     */
    void nuevaLinea(PaqueteDTO paquete);
    
    /**
     * Recibe el evento de TurnoActualizado con el jugador en turno.
     * Actualiza el jugador en turno en la lista de jugadores.
     * @param paquete
     */
    void turnoActualizado(PaqueteDTO paquete);
    
    /**
     * Recibe el evento de InicioPartida con la lista de jugadores conectados.
     * Establece la lista actual de jugadores.
     */
    void inicioPartida();
    
    /**
     * Recibe el evento de ActualizarPuntos con la lista de jugadores conectados.
     * Establece el score actual te los jugadore en tiempo real.
     * @param paquete
     */
    void actualizarPuntos(PaqueteDTO paquete);
}
