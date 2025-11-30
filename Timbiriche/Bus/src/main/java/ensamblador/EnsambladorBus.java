package ensamblador;

import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import EventBus.EventBus;
import PublicadorEventos.PublicadorEventos;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PaqueteDTO;
import org.itson.dto.PartidaDTO;
import org.itson.dto.TableroDTO;

/**
 * Ensamblador para iniciar el EventBus como servicio independiente.
 *
 * @author victoria
 */
public class EnsambladorBus {

    private String host;
    private int puertoEntrada;
    private int puertoBus;

    private EventBus eventBus;
    private IEmisor emisorBus;

    public EnsambladorBus(String configFile) throws IOException {
        Properties props = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream(configFile);
        if (input == null) {
            throw new IOException("No se encontro el archivo de configuracion: " + configFile);
        }
        props.load(input);

        this.host = props.getProperty("host");
        this.puertoEntrada = Integer.parseInt(props.getProperty("puerto.entrada"));
        this.puertoBus = Integer.parseInt(props.getProperty("puerto.bus"));
    }

    public void iniciar() {
        eventBus = new EventBus();

        // emisor del bus
        ColaEnvios colaEnviosBus = new ColaEnvios();
        ClienteTCP clienteTCPBus = new ClienteTCP(colaEnviosBus, puertoBus, host);
        colaEnviosBus.agregarObservador(clienteTCPBus);
        emisorBus = new Emisor(colaEnviosBus);
        eventBus.setEmisor(emisorBus);

        // receptor
        ColaRecibos colaRecibosBus = new ColaRecibos();
        ServidorTCP servidorTCPBus = new ServidorTCP(colaRecibosBus, puertoEntrada);
        IReceptor publicador = new PublicadorEventos(puertoBus, host, eventBus);
        Receptor receptorBus = new Receptor();
        receptorBus.setCola(colaRecibosBus);
        receptorBus.setReceptor(publicador);
        colaRecibosBus.agregarObservador(receptorBus);

        // configuraciones de la partida :P
        JugadorDTO jugador1 = new JugadorDTO();
        jugador1.setListo(false);
        jugador1.setId("sol");
        jugador1.setAvatar("TIBURON_MARTILLO");
        jugador1.setColor("MORAS");

        TableroDTO tablero = new TableroDTO(10, 10);
        PaqueteDTO tableroConfig = new PaqueteDTO(tablero, "REGISTRAR_TABLERO");
        PaqueteDTO configuraciones = new PaqueteDTO(jugador1, "REGISTRAR_JUGADOR");
        
        eventBus.setConfiguracionJugadores(configuraciones);
        eventBus.setConfiguracionesTablero(tableroConfig);



        new Thread(() -> servidorTCPBus.iniciar()).start();
        System.out.println("[EventBus] Servicio iniciado en puerto " + puertoEntrada);
    }

    public static void main(String[] args) {
        try {
            EnsambladorBus ensamblador = new EnsambladorBus("config_bus.properties");
            ensamblador.iniciar();
        } catch (IOException e) {
            System.err.println("Error al iniciar el EventBus: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
