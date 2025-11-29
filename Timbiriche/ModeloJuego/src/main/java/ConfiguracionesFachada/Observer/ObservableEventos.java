package ConfiguracionesFachada.Observer;

import Observer.ObservadorEventos;
import org.itson.dto.PartidaDTO;

/**
 *
 * @author victoria
 */
public interface ObservableEventos {
    void agregarObservador(ObservadorEventos ob);
    void notificarInicioJuego(PartidaDTO partida);
}
