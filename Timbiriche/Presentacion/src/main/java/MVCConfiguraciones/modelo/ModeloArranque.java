package MVCConfiguraciones.modelo;

import Fachada.ConfiguracionesFachada;
import MVCConfiguraciones.observer.Observable;
import MVCConfiguraciones.observer.Observador;
import configuracionesPartida.ConfiguracionesPartida;
import org.itson.dto.ConfiguracionesDTO;

/**
 *
 * @author victoria
 */
public class ModeloArranque implements IModeloArranqueExcritura, IModeloArranqueLectura, Observable{
    private Observador observadorConfiguraciones;
    private ConfiguracionesFachada configuracion;
    
    @Override
    public void guardarConfiguracion(ConfiguracionesDTO cofiguracion) {
        configuracion.setConfiguraciones(cofiguracion);
    }
    
}
