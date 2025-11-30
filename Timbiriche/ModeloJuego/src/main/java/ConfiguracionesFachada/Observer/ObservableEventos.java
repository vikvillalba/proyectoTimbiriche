package ConfiguracionesFachada.Observer;

import org.itson.dto.PartidaDTO;

/**
 *
 * @author victoria
 */
public interface ObservableEventos {

    void agregarObservador(ObservadorEventos ob);

    void notificarEventoRecibido(PartidaDTO evento);

    void notificarInicioJuego(PartidaDTO partida);
}
