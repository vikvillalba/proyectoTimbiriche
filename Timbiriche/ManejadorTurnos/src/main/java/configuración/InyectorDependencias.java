package configuración;

import EnzambladorRedEventBus.EnzambladorRedEventBus;
import org.itson.componentereceptor.IReceptor;

/**
 *
 * @author pablo
 */
public class InyectorDependencias {

    private IReceptor receptor;

    public void setReceptor(IReceptor receptor) {
        this.receptor = receptor;
    }

    /**
     * Inyecta el receptor dentro del ensamblador de red, que a su vez creará el
     * servidor TCP y registrará el IReceptor.
     * @param host
     * @param puertoEntrada
     */
    public void conectarConRed(String host, int puertoEntrada) {
        EnzambladorRedEventBus ensambladorRed = EnzambladorRedEventBus.getInstancia().configurar(host, puertoEntrada, receptor);
        ensambladorRed.ensamblar();
    }

}
