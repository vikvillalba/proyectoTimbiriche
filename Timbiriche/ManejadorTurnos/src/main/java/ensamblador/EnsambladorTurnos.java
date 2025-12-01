package ensamblador;

import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import Turnos.ManejadorTurnos;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PaqueteDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Ensamblador para iniciar el ManejadorTurnos como servicio independiente.
 *
 * @author victoria
 */
public class EnsambladorTurnos {

    private String host;
    private int puertoEntrada;
    private int puertoTurnos;

    public EnsambladorTurnos(String configFile) throws IOException {
        Properties props = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream(configFile);
        if (input == null) {
            throw new IOException("No se encontro el archivo de configuracion: " + configFile);
        }
        props.load(input);

        this.host = props.getProperty("host");
        this.puertoEntrada = Integer.parseInt(props.getProperty("puerto.entrada"));
        this.puertoTurnos = Integer.parseInt(props.getProperty("puerto.turnos"));
    }

    public void iniciar() {
        ManejadorTurnos manejador = new ManejadorTurnos();
        manejador.setHost(host);
        manejador.setPuertoOrigen(puertoTurnos);
        manejador.setPuertoDestino(puertoEntrada);

        ColaRecibos colaRecibosTurnero = new ColaRecibos();
        ServidorTCP servidorTurnero = new ServidorTCP(colaRecibosTurnero, puertoTurnos);
        System.out.println("[Turnero] Servidor escuchando en puerto " + puertoTurnos);

        ColaEnvios colaEnviosTurnos = new ColaEnvios();
        IEmisor emisor = new Emisor(colaEnviosTurnos);
        ClienteTCP clienteTurnos = new ClienteTCP(colaEnviosTurnos, puertoEntrada, host);
        colaEnviosTurnos.agregarObservador(clienteTurnos);
        manejador.setEmisor(emisor);

        Receptor receptorTurnero = new Receptor();
        receptorTurnero.setCola(colaRecibosTurnero);
        receptorTurnero.setReceptor(manejador);

        colaRecibosTurnero.agregarObservador(receptorTurnero);
        new Thread(() -> servidorTurnero.iniciar()).start();

        // le indica al bus a que eventos esta suscrito
        List<String> eventos = Arrays.asList(
                "SOLICITAR_TURNOS",
                "ACTUALIZAR_TURNO",
                "ABANDONAR_PARTIDA"
        );

        PaqueteDTO paquete = new PaqueteDTO(eventos, "INICIAR_CONEXION");
        paquete.setHost(host);
        paquete.setPuertoOrigen(puertoTurnos);
        paquete.setPuertoDestino(puertoEntrada);
        emisor.enviarCambio(paquete);
    }

    public static void main(String[] args) {
        try {
            EnsambladorTurnos ensamblador = new EnsambladorTurnos("config_turnos.properties");
            ensamblador.iniciar();
        } catch (IOException e) {
            System.err.println("Error al iniciar el ManejadorTurnos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
