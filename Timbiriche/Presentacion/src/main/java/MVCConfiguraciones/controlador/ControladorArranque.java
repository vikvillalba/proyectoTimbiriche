package MVCConfiguraciones.controlador;

import MVCConfiguraciones.modelo.IModeloArranqueEscritura;
import org.itson.dto.JugadorDTO;

/**
 *
 * @author victoria
 */
public class ControladorArranque {

    private IModeloArranqueEscritura modelo;
    

    public ControladorArranque(IModeloArranqueEscritura modelo) {
        this.modelo = modelo;
    }

    public void registrarJugador(JugadorDTO jugador) {
        modelo.registrarJugador(jugador);
    }
    
    public void solicitarElementosUso() {
        modelo.solicitarElementosUso();
    }
}
