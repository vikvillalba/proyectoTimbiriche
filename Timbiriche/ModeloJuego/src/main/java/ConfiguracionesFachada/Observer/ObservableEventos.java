package ConfiguracionesFachada.Observer;

import org.itson.dto.PartidaDTO;

/**
 *
 * @author victoria
 */
public interface ObservableEventos {

    void agregarObservador(ObservadorEventos ob);

    void notificarEventoRecibido(PartidaDTO evento);

    void notificarInicioJuego();
    
    void agregarObservadorSolicitudInicio(ObservadorSolicitudInicio ob);
    void notificarSolictudInicio(PartidaDTO partida);
}
