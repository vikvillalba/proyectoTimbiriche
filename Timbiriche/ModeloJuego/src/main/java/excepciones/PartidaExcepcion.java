package excepciones;

/**
 * Excepcion que se lanza cuando los dos puntos seleccionados no son adyacentes.
 * @author victoria
 */
public class PartidaExcepcion extends Exception{

    public PartidaExcepcion(String message) {
        super(message);
    }
    
}
