package Observer;

import Entidades.Jugador;
import java.util.List;

/**
 * 
 * @author victoria
 */
public interface ObservadorTurnos {
    void actualizar(List<Jugador> jugadores);
}
