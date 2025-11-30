package ConfiguracionesFachada;

import Entidades.Jugador;
import java.util.List;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PaqueteDTO;
import org.itson.dto.PartidaDTO;
import org.itson.dto.TableroDTO;

/**
 *
 * @author victoria
 */
public interface ConfiguracionesFachada {
    void  solicitarConfiguraciones();
    void iniciarConexion(List<Jugador> jugadores, TableroDTO tablero, Jugador sesion);
    void solicitarInicioJuego(JugadorDTO jugador);
    void confirmarIncioJuego(JugadorDTO jugador);
    PartidaDTO getConfiguraciones();
}
