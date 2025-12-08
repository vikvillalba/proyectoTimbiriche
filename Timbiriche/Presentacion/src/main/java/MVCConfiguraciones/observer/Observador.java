package MVCConfiguraciones.observer;

import org.itson.dto.ConfiguracionesDTO;

/**
 *
 * @author victoria
 */
public interface Observador {
    
    void cambioConfiguracion(ConfiguracionesDTO configuracion);
}
