package MVCConfiguracion.observer;

import objetosPresentables.PartidaPresentable;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PartidaDTO;

/**
 *
 * @author victoria
 */
public interface ObservadorEventoInicio {
    void iniciar(PartidaDTO partida, JugadorDTO sesion);
}
