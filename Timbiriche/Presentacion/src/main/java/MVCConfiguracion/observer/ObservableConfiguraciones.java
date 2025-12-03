package MVCConfiguracion.observer;

import objetosPresentables.PartidaPresentable;
import org.itson.dto.PartidaDTO;

/**
 *
 * @author victoria
 */
public interface ObservableConfiguraciones {
    void agregarObservadorConfiguraciones(ObservadorConfiguraciones ob);
    void agregarObservadorEventoInicio(ObservadorEventoInicio ob);
    void notificarInicioPartida();
    void notificarConfiguraciones(PartidaPresentable partida);
    void agregarObservadorSolicitudes(ObservadorSolicitudes ob);
    void notificarObservadorSolicitudes();
}   
