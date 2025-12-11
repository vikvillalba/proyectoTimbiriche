package PublicadorEventos;

import EventBus.EventBus;
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
        String tipoEvento = paquete.getTipoEvento();

        System.out.println("[PublicadorEventos] Evento recibido: " + tipoEvento);

        // Manejar eventos especiales
        switch (tipoEvento) {
            case "OBTENER_JUGADOR_SALA":
                eventBus.enviarJugadorEnSala(paquete);
                return;

            case "SOLICITAR_UNIRSE":
                // Iniciar proceso de consenso
                eventBus.iniciarConsensoSolicitud(paquete);
                return;

            case "VOTAR_SOLICITUD":
                // Procesar voto de un jugador
                eventBus.procesarVotoSolicitud(paquete);
                return;

            default:
                // Para otros eventos, usar el flujo normal
                eventBus.publicarEvento(paquete);
        }
    }

    public int getPuerto() {
        return puerto;
    }

    public String getHost() {
        return host;
    }

}
