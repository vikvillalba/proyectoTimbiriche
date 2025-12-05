package MVCConfiguraciones.modelo;

import MVCConfiguraciones.observer.Observador;
import org.itson.dto.ConfiguracionesDTO;

/**
 *
 * @author victoria
 */
public class ModeloArranque implements IModeloArranqueExcritura, IModeloArranqueLectura, Observador{

    @Override
    public void guardarConfiguracion(ConfiguracionesDTO cofiguracion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
