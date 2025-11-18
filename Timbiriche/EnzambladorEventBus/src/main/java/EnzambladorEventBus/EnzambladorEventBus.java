package EnzambladorEventBus;

import EventBus.EventBus;
import IEmisorBus.IEmisorBus;
import PublicadorEventos.PublicadorEventos;

import ReceptorEventos.ReceptorEventos;
import org.itson.componentereceptor.IReceptor;

/**
 *
 *
 * @author Jack Murrieta
 */
public class EnzambladorEventBus {
//
//    private static EnzambladorEventBus instancia;
//
//    private String host;
//    private int puertoEntrada;
//
//    private EventBus eventBus;
//
//    private PublicadorEventos publicadorEventos;
//
//    private EnzambladorEventBus() {
//    }
//
//    // Singleton
//    public static synchronized EnzambladorEventBus getInstancia() {
//        if (instancia == null) {
//            instancia = new EnzambladorEventBus();
//        }
//        return instancia;
//    }
//
//    /**
//     * Configura valores antes de ensamblar.
//     */
//    public EnzambladorEventBus configurar(String host, int puertoEntrada) {
//        this.host = host;
//        this.puertoEntrada = puertoEntrada;
//        return this;
//    }
//
//    /**
//     * Ensambla EventBus + componentes de red.
//     */
//    public void ensamblar() {
//
//        System.out.println("[EnsambladorEventBus] Iniciando con:");
//        System.out.println(" Host: " + host);
//        System.out.println(" Puerto Entrada: " + puertoEntrada);
//
//        // Crear EventBus
//        this.eventBus = new EventBus(puertoEntrada, host);
//
//        // Receptor adaptable del EventBus
//        IReceptor adaptadorReceptor = new ReceptorEventos(eventBus);
//
//        // Configurar la capa de red en EnsambladorRed
//        EnzambladorRedEventBus.EnzambladorRedEventBus red = EnzambladorRedEventBus.EnzambladorRedEventBus.getInstancia()
//                .configurar(host, puertoEntrada, adaptadorReceptor);
//
//        red.ensamblar(); // crea servidor, cliente y emisor
//
//        // Obtener el emisor final
//        IEmisorBus emisor = red.getEmisor();
//
//        // Inyectar publicador de eventos al EventBus
//        this.publicadorEventos = new PublicadorEventos(emisor);
//        eventBus.setPublicadorEventos(publicadorEventos);
//
//        System.out.println("[EnsambladorEventBus] EventBus ensamblado correctamente.");
//    }
//
//    public EventBus getEventBus() {
//        return eventBus;
//    }
//
//    public String getHost() {
//        return host;
//    }
//
//    public int getPuertoEntrada() {
//        return puertoEntrada;
//    }
//
//    public PublicadorEventos getPublicadorEventos() {
//        return publicadorEventos;
//    }

}
