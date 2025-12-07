/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConfiguracionesFachada;

import Configuraciones.Observer.ObservableEvento;
import Configuraciones.Observer.ObserverEvento;
import java.util.List;
import org.itson.componenteemisor.IEmisor;

/**
 *
 * @author Maryr
 */
public class ConfiguracionesPartida implements ConfiguracionesFachada, ObservableEvento{

    private IEmisor emisor;
    private String host;
    private int puertoOrigen;
    private int puertoDestino;
    private ObserverEvento observer;

    @Override
    public void agregarObserver(ObserverEvento o) {
        this.observer = o;
    }

    @Override
    public void notificarObserver(List<String> usados) {
        observer.validarJugador(usados);
    }
    

}
