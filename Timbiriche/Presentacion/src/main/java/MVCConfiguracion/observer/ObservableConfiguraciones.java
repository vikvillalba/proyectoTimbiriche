package MVCConfiguracion.observer;

import objetosPresentables.PartidaPresentable;

/**
 *
 * @author victoria
 */
public interface ObservableConfiguraciones {
    void agregarObservadorConfiguraciones(ObservadorConfiguraciones ob);
    void agregarObservadorEventoInicio(ObservadorEventoInicio ob);
    void notificarInicioPartida(PartidaPresentable partida);
    void notificarConfiguraciones(PartidaPresentable partida);
}   
