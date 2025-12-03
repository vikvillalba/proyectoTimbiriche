package ConfiguracionesFachada;

import org.itson.dto.JugadorDTO;
import org.itson.dto.PartidaDTO;

/**
 *
 * @author victoria
 */
public interface ConfiguracionesFachada {
    void  solicitarConfiguraciones();
    void solicitarInicioJuego(JugadorDTO jugador);
    void confirmarIncioJuego(JugadorDTO jugador);
    PartidaDTO getConfiguraciones();
}
