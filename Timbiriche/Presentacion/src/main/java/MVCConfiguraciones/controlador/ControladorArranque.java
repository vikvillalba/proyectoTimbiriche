package MVCConfiguraciones.controlador;

import MVCConfiguraciones.modelo.IModeloArranqueExcritura;
import MVCConfiguraciones.modelo.ModeloArranque;

/**
 *
 * @author victoria
 */
public class ControladorArranque {
    IModeloArranqueExcritura modelo = new ModeloArranque();
    
    public void manejarConfiguración(int numJugadores, String tam){};
}
