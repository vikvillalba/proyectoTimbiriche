package MVCConfiguraciones.controlador;

import MVCConfiguraciones.modelo.IModeloArranqueEscritura;
import java.util.List;
import org.itson.dto.JugadorNuevoDTO;

/**
 *
 * @author victoria
 */
public class ControladorArranque {

    private IModeloArranqueEscritura modelo;
    

    public ControladorArranque(IModeloArranqueEscritura modelo) {
        this.modelo = modelo;
    }

    public void registrarJugador(JugadorNuevoDTO jugador) {
        System.out.println("[controlador] registrar jugador: " + jugador.getNombre() + " " + jugador.getColor() + " " + jugador.getAvatar());
        modelo.registrarJugador(jugador);
    }
    
    public void solicitarElementosUso() {
        modelo.solicitarElementosUso();
    }
    
    //solo para prueba
    public void enviarElementosUsados(List<String> usados) {
        modelo.enviarElementosUsados(usados);
    }
}
