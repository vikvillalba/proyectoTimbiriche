package ensambladorGeneral;

import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Entidades.Jugador;
import Entidades.TipoEvento;
import EventBus.EventBus;
import Fachada.Partida;
import MVCJuegoEnCurso.controlador.ControladorPartida;
import MVCJuegoEnCurso.modelo.implementaciones.ModeloPartida;
import MVCJuegoEnCurso.modelo.interfaces.IModeloJugadoresLectura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloTableroLectura;
import MVCJuegoEnCurso.vista.FrmPartida;
import Mapper.MapperJugadores;
import PublicadorEventos.PublicadorEventos;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import Turnos.ManejadorTurnos;
import configuraciones.Configuraciones;
import java.util.Arrays;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Maryr
 */
public class EnsambladorGeneral {

    private static EnsambladorGeneral instancia;

    // Config
    private String host;
    private int puertoEntrada; //donde se conectan los clientes para mandar eventos
    private int puertoBus; // donde el bus envia los eventos
    private int puertoServidor;
    private int puertoTurnos;

    private MapperJugadores mapper;

    private static EventBus eventBus;
    private static IEmisor emisorBus;

    private final int NUMERO_JUGADORES = 4;

    private EnsambladorGeneral(Configuraciones config) {
        this.mapper = new MapperJugadores();

        this.host = config.getString("host");
        this.puertoEntrada = config.getInt("puerto.entrada");
        this.puertoBus = config.getInt("puerto.bus");
        this.puertoServidor = config.getInt("puerto.servidor");
        this.puertoTurnos = config.getInt("puerto.turnos");
    }

    public static EnsambladorGeneral getInstancia(String configName) {
        try {
            Configuraciones loader = new Configuraciones(configName);
            instancia = new EnsambladorGeneral(loader);
            return instancia;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Método que inicializa una nueva partida con configuraciones brindadas por
     * el usuario.
     */
    public void iniciarPartida(List<Jugador> jugadores, int alto, int ancho, Jugador sesion) {
        if (jugadores.size() > NUMERO_JUGADORES) {
            throw new IllegalArgumentException("Se ha alcanzado el límite de jugadores");

        }

        List<JugadorDTO> jugadoresdto = mapper.toListaDTO(jugadores);
        if (!jugadores.isEmpty()) {
            jugadores.get(0).setTurno(true);
        }

        Partida partida = new Partida(jugadores, alto, ancho);
        partida.setHost(host);     // host del ensamblador
        partida.setPuertoOrigen(puertoServidor);
        partida.setPuertoDestino(puertoEntrada);

        // emisor del servicio
        ColaEnvios colaEnvios = new ColaEnvios();
        IEmisor emisor = new Emisor(colaEnvios);
        ClienteTCP clientePartida = new ClienteTCP(colaEnvios, puertoEntrada, host);
        colaEnvios.agregarObservador(clientePartida);

        // receptor del servicio
        ColaRecibos colaRecibos = new ColaRecibos();
        ServidorTCP servidorPartida = new ServidorTCP(colaRecibos, puertoServidor);
        partida.setEmisor(emisor);

        Receptor receptorPartida = new Receptor();
        receptorPartida.setCola(colaRecibos);
        receptorPartida.setReceptor(partida);

        List<String> eventos = Arrays.asList(
                "JUGADA_REALIZADA",
                "INICIO_PARTIDA"
        );

        PaqueteDTO solicitarConexion = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        solicitarConexion.setHost(host);
        solicitarConexion.setPuertoOrigen(puertoServidor);
        solicitarConexion.setPuertoDestino(puertoEntrada);
        emisor.enviarCambio(solicitarConexion);

        PaqueteDTO solicitarTurnos = new PaqueteDTO(jugadoresdto, "SOLICITAR_TURNOS");
        solicitarTurnos.setHost(host);
        solicitarTurnos.setPuertoOrigen(puertoServidor);
        solicitarTurnos.setPuertoDestino(puertoEntrada);
        emisor.enviarCambio(solicitarTurnos);

        new Thread(() -> servidorPartida.iniciar()).start();

        //MVC de arranque
        partida.setJugadorSesion(sesion);
        ModeloPartida modelo = new ModeloPartida(partida);
        IModeloJugadoresLectura imjl = modelo;
        IModeloTableroLectura imtl = modelo;
        IModeloPartidaEscritura impe = modelo;

        ControladorPartida controlador = new ControladorPartida(impe);
        FrmPartida frm = new FrmPartida(imjl, imtl, controlador);
        partida.agregarObservadorInicioJuego(modelo);
       
        modelo.agregarObservadorJugadores(frm.getObservadorJugadores());
        modelo.agregarObservadorTablero(frm.getObservadorTablero());
        modelo.agregarObservadorInicioJuego(frm);
        partida.notificarObservadorInicioJuego();

//        //Sirve para registrar la lista de jugadores en el turnero(es por mientras)
//        new Thread(() -> {
//            try {
//                //Espera 8 segundos
//                Thread.sleep(8000);
//                PaqueteDTO paquete1 = new PaqueteDTO(jugadoresdto, "SOLICITAR_TURNOS");
//                paquete1.setHost(host);
//                paquete1.setPuertoOrigen(puertoServidor);
//                paquete1.setPuertoDestino(puertoEntrada);
//                emisor.enviarCambio(paquete1);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                System.err.println("El hilo fue interrumpido.");
//            }
//        }).start();
    }

    public void iniciarBus() {
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
        new Thread(() -> servidorTCPBus.iniciar()).start();
        System.out.println("EventBus levantao");
    }

    public void iniciarManejadorTurnos() {
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
                "ACTUALIZAR_TURNO"
        );

        PaqueteDTO paquete = new PaqueteDTO(eventos, "INICIAR_CONEXION");
        paquete.setHost(host);
        paquete.setPuertoOrigen(puertoTurnos);
        paquete.setPuertoDestino(puertoEntrada);
        emisor.enviarCambio(paquete);
    }
}
