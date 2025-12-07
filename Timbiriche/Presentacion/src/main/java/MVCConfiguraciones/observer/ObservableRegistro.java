package MVCConfiguraciones.observer;

import java.util.List;

/**
 *
 * @author victoria
 */
public interface ObservableRegistro {

    void agregarObserver(ObserverRegistro o);

    void notificarObserver(List<String> usados);
}
