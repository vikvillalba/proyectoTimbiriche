package configuraciones;

import Configuraciones.ConfiguracionesPartida;
import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import Entidades.TipoEvento;
import Fachada.ConfiguracionesFachada;
import Fachada.Partida;
import Fachada.PartidaComunicacion;
import MVCConfiguraciones.controlador.ControladorArranque;
import MVCConfiguraciones.modelo.IModeloArranqueExcritura;
import MVCConfiguraciones.modelo.ModeloArranque;
import MVCConfiguraciones.vista.frmConfigurarPartida;
import MVCJuegoEnCurso.controlador.ControladorPartida;
import MVCJuegoEnCurso.modelo.implementaciones.ModeloPartida;
import MVCJuegoEnCurso.modelo.interfaces.IModeloJugadoresLectura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloTableroLectura;
import MVCJuegoEnCurso.vista.FrmPartida;
import Mapper.MapperJugadores;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PaqueteDTO;
import org.itson.observadores.ConfiguracionObservable;
import org.itson.observadores.ObservadorConfiguracionLocal;

import java.util.Properties;
import java.io.InputStream;
import org.itson.dto.JugadorDTO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author erika
 */
public class EnsambladorJuegoConfig {

    private static Jugador jugadorSesionLocal;
    private static Partida partidaUnica;
    private MapperJugadores mapper;

    private static List<Jugador> jugadoresIniciales;

    private boolean esHostDePartida;

    // Config
    private String host;
    private int puertoEntrada; // Puerto de escucha local
    private int puertoServicio; // Puerto del Bus
    private int puertoTurnos;

    private IEmisor emisor;

    public EnsambladorJuegoConfig(String configFile, boolean esHost) throws IOException {
        this.mapper = new MapperJugadores();
        Properties props = new Properties();

        InputStream input = getClass().getClassLoader().getResourceAsStream(configFile);

        if (input == null) {
            throw new IOException("No se encontro el archivo de configuracion: " + configFile);
        }
        props.load(input);

        this.host = props.getProperty("host");
        this.puertoEntrada = Integer.parseInt(props.getProperty("puerto.cliente.entrada"));
        this.puertoServicio = Integer.parseInt(props.getProperty("puerto.bus.entrada"));
        this.puertoTurnos = Integer.parseInt(props.getProperty("puerto.turnos.entrada"));

        this.esHostDePartida = esHost;

        System.out.println("[Configuración] Host: " + this.host + ", Puerto Local: " + this.puertoEntrada + ", Puerto Bus: " + this.puertoServicio + ", Puerto Turnos: " + this.puertoTurnos);
    }

    public EnsambladorJuegoConfig(String configFile) throws IOException {
        this(configFile, false); // Por defecto, no es host si se usa el constructor simple
    }

    /**
     * Inicia el cliente para la partida, estableciendo la sesión local y el
     * archivo de configuración.
     *
     * @param jugadorSesion El jugador que controla este cliente.
     * @param configFile El archivo de propiedades de red para este cliente.
     */
    public static void iniciar(Jugador jugadorSesion, String configFile) {
        setJugadorSesion(jugadorSesion);

        EventQueue.invokeLater(() -> {
            try {
                EnsambladorJuegoConfig ensamblador = new EnsambladorJuegoConfig(configFile);
                ensamblador.ensamblar();
            } catch (Exception e) {
                System.err.println("Fallo crítico en el ensamblaje: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     *
     * Inicia el ensamblador con la lista completa de jugadores inyectada desde
     * el Main.
     */
    public static void iniciarPartidaCompleta(Jugador jugadorSesion, String configFile, List<Jugador> jugadores, boolean esHost) {
        setJugadorSesion(jugadorSesion);
        EnsambladorJuegoConfig.jugadoresIniciales = jugadores; // Guardar la lista

        EventQueue.invokeLater(() -> {
            try {
                // **SE PASA EL NUEVO PARÁMETRO DE ROL DE HOST**
                EnsambladorJuegoConfig ensamblador = new EnsambladorJuegoConfig(configFile, esHost);
                ensamblador.ensamblar();
            } catch (Exception e) {
                System.err.println("Fallo crítico en el ensamblaje: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public static void setJugadorSesion(Jugador jugador) {
        jugadorSesionLocal = jugador;
    }

    private void ensamblar() throws Exception {

        partidaUnica = new Partida(jugadoresIniciales, 10, 10);
        partidaUnica.setHost(host);
        partidaUnica.setPuertoOrigen(puertoEntrada);
        partidaUnica.setPuertoDestino(puertoServicio);
        partidaUnica.setJugadorSesion(jugadorSesionLocal);
        partidaUnica.setPuertoTurnos(puertoTurnos);

        ColaEnvios colaEnvios = new ColaEnvios();
        this.emisor = new Emisor(colaEnvios);
        partidaUnica.setEmisor(emisor);

        ColaRecibos colaRecibos = new ColaRecibos();
        ClienteTCP clientePartida = new ClienteTCP(colaEnvios, puertoServicio, host);
        colaEnvios.agregarObservador(clientePartida);
        ServidorTCP servidorPartida = new ServidorTCP(colaRecibos, puertoEntrada);
        PartidaComunicacion partidaComunicacion = new PartidaComunicacion();
        partidaComunicacion.setPartida(partidaUnica);
        Receptor receptorPartida = new Receptor();
        receptorPartida.setCola(colaRecibos);
        receptorPartida.setReceptor(partidaComunicacion);
        colaRecibos.agregarObservador(receptorPartida);

        new Thread(() -> servidorPartida.iniciar()).start();
        iniciarConexion(emisor);

        System.out.println("[Ensamblador] Partida inicializada con " + jugadoresIniciales.size() + " jugadores.");

        ModeloPartida modeloJuego = new ModeloPartida(partidaUnica);
        ControladorPartida controladorJuego = new ControladorPartida((IModeloPartidaEscritura) modeloJuego);
        FrmPartida frmPartida = new FrmPartida((IModeloJugadoresLectura) modeloJuego, (IModeloTableroLectura) modeloJuego, controladorJuego);

        partidaUnica.agregarObservadorInicioJuego(modeloJuego);
        partidaUnica.agregarObservadorJugadores(modeloJuego);
        partidaUnica.agregarObservadorEventos(modeloJuego);

        modeloJuego.agregarObservadorInicioJuego(frmPartida);
        modeloJuego.agregarObservadorJugadores(frmPartida.getObservadorJugadores());
        modeloJuego.agregarObservadorTablero(frmPartida.getObservadorTablero());

        if (esHostDePartida) {

            ConfiguracionesPartida configuradorRed = new ConfiguracionesPartida();
            configuradorRed.setEmisor(emisor);

            if (partidaComunicacion instanceof ObservadorConfiguracionLocal) {
                ((ConfiguracionObservable) configuradorRed).agregarObservadorLocal((ObservadorConfiguracionLocal) partidaComunicacion);
            }

            ConfiguracionesFachada publicadorConfiguracion = configuradorRed;
            ModeloArranque modeloArranque = new ModeloArranque(publicadorConfiguracion);
            ControladorArranque controladorArranque = new ControladorArranque(
                    (ModeloArranque) (IModeloArranqueExcritura) modeloArranque
            );

            frmConfigurarPartida frmConfig = new frmConfigurarPartida(controladorArranque);

            partidaUnica.agregarObservadorInicioJuego(() -> {
                EventQueue.invokeLater(() -> {
                    System.out.println("[Ensamblador] Host: Notificación de inicio de juego recibida. Cambiando de Frame...");
                    //frmConfig.dispose();
                    frmPartida.setVisible(true);
                });
            });

            //partidaUnica.inicioPartida();
            //solicitarInicioTurnos();
            frmConfig.setVisible(true);

        } else {

            partidaUnica.agregarObservadorInicioJuego(() -> {
                EventQueue.invokeLater(() -> {
                    System.out.println("[Ensamblador] Cliente: Notificación de inicio de juego recibida. El juego ha comenzado.");
                });
            });

            System.out.println("[Ensamblador] Cliente: Mostrando Frame de Partida en modo Espera.");
            frmPartida.setVisible(true);
        }
    }

    private void iniciarConexion(IEmisor emisor) {
        List<String> eventos = Arrays.asList(
                "JUGADA_REALIZADA", "TURNO_ACTUALIZADO", "INICIO_PARTIDA",
                "NUEVA_LINEA", "UNIRSE_PARTIDA", "ABANDONAR_PARTIDA",
                "CONFIGURAR_PARTIDA", "SOLICITAR_FINALIZAR_PARTIDA", "ACTUALIZAR_PUNTOS",
                "ACTUALIZAR_LISTA_JUGADORES"
        );

        PaqueteDTO solicitarConexion = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        solicitarConexion.setHost(host);
        solicitarConexion.setPuertoOrigen(puertoEntrada);
        solicitarConexion.setPuertoDestino(puertoServicio);
        emisor.enviarCambio(solicitarConexion);
    }
}
