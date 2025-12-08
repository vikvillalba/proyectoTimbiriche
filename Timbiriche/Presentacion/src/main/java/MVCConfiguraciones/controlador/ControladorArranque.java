package MVCConfiguraciones.controlador;

import MVCConfiguraciones.modelo.IModeloArranqueExcritura;
import MVCConfiguraciones.modelo.ModeloArranque;
import org.itson.dto.ConfiguracionesDTO;

/**
 *Clase Controladora que gestiona la interacción entre la Vista (frmConfigurarPartida)
 * y el Modelo de Arranque.
 * @author victoria
 */
public class ControladorArranque {
   private ModeloArranque modelo;
   
   public ControladorArranque(ModeloArranque modelo) {
        this.modelo = modelo;
    }
    
    /**
     * Método principal llamado por la Vista 
     * Procesa los datos de la configuración del tablero.
     * @param numJugadores El número de jugadores seleccionado.
     * @param tam El tamaño del tablero seleccionado (ej: "10 x 10").
     */
    public void manejarConfiguración(int numJugadores, String tam) {
        ConfiguracionesDTO dto = new ConfiguracionesDTO(numJugadores, tam);
        
        modelo.guardarConfiguracion(dto);
        modelo.publicarConfiguracion(dto); 
    }
    
    
}
