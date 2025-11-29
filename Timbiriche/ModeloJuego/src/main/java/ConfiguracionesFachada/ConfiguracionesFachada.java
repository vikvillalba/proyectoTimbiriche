package ConfiguracionesFachada;

import Entidades.Jugador;
import java.util.List;
import org.itson.dto.PartidaDTO;
import org.itson.dto.TableroDTO;

/**
 *
 * @author victoria
 */
public interface ConfiguracionesFachada {
    PartidaDTO obtenerConfiguraciones();
    void iniciarConexion(List<Jugador> jugadores, TableroDTO tablero, Jugador sesion);
    void solicitarInicioJuego(Jugador jugador);
    void confirmarIncioJuego(Jugador jugador);
}
