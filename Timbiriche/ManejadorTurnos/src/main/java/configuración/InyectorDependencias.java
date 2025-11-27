package configuración;

import Turnos.ManejadorTurnos;
import org.itson.componenteemisor.IEmisor;

/**
 *
 * @author pablo
 */
public class InyectorDependencias {
    
    public ManejadorTurnos setEmisor(IEmisor emisor){
        ManejadorTurnos manejador = new ManejadorTurnos();
        manejador.setEmisor(emisor);
        return manejador;
    }
}
