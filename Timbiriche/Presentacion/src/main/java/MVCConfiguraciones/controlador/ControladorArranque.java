package MVCConfiguraciones.controlador;

import MVCConfiguraciones.modelo.IModeloArranqueExcritura;
import MVCConfiguraciones.modelo.ModeloArranque;
import org.itson.dto.ConfiguracionesDTO;

/**
 *
 * @author victoria
 */
public class ControladorArranque {
    IModeloArranqueExcritura modelo = new ModeloArranque();
    
    public void manejarConfiguración(int numJugadores, String tam){};
    
    public void publicarConfiguracion(ConfiguracionesDTO configuracion){
        modelo.guardarConfiguracion(configuracion);
    };
}
