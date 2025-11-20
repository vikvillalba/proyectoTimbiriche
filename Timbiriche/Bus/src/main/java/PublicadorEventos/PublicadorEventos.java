package PublicadorEventos;

import EventBus.EventBus;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class PublicadorEventos implements IReceptor {

    private int puerto;
    private String host;
    private EventBus eventBus;

    public PublicadorEventos(int puerto, String host, EventBus eventBus) {
        this.puerto = puerto;
        this.host = host;
        this.eventBus = eventBus;
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        eventBus.publicarEvento(paquete);
    }

    public int getPuerto() {
        return puerto;
    }

    public String getHost() {
        return host;
    }

}
