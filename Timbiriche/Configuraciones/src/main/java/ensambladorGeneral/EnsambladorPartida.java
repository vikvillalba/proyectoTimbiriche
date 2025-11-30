package ensambladorGeneral;

import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Entidades.Jugador;
import Entidades.TipoEvento;
import Fachada.Partida;
import Fachada.PartidaComunicacion;
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
import configuraciones.Configuraciones;
import ConfiguracionesFachada.ConfiguracionesPartida;
import MVCConfiguracion.controlador.ControladorArranque;
import MVCConfiguracion.modelo.ModeloArranque;
import MVCConfiguracion.vista.FrmSalaEspera;
import java.util.Arrays;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PaqueteDTO;

/**
 * Ensamblador general para iniciar partidas del juego.
 *
 * @author Maryr
 */
public class EnsambladorPartida {

    private static EnsambladorPartida instancia;

    // Config
    private String host;
    private int puertoEntrada;
    private int puertoServidor;
    private MapperJugadores mapper;

    private final int NUMERO_JUGADORES = 4;

    //componentes de la partida
    private PartidaComunicacion partidaComunicacion;
    private ConfiguracionesPartida configuraciones;
    private Partida partida;
    private ColaEnvios colaEnvios;
    private IEmisor emisor;
    private ClienteTCP clientePartida;
    private ColaRecibos colaRecibos;
    ServidorTCP servidorPartida;
    Receptor receptorPartida;

    // componentes del mvc
    private ModeloPartida modelo;
    private ControladorPartida controlador;

    private EnsambladorPartida(Configuraciones config) {
        this.mapper = new MapperJugadores();

        this.host = config.getString("host");
        this.puertoEntrada = config.getInt("puerto.entrada");
        this.puertoServidor = config.getInt("puerto.servidor");
    }

    public static EnsambladorPartida getInstancia(String configName) {
        try {
            Configuraciones loader = new Configuraciones(configName);
            instancia = new EnsambladorPartida(loader);
            return instancia;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void configurarPartida(JugadorDTO sesion) {
        partidaComunicacion = new PartidaComunicacion();
        configuraciones = new ConfiguracionesPartida();
        partida = new Partida();

        configuraciones.setHost(host);
        configuraciones.setPuertoOrigen(puertoServidor);
        configuraciones.setPuertoDestino(puertoEntrada);

        partidaComunicacion.setConfiguraciones(configuraciones);

        // emisor del servicio
        colaEnvios = new ColaEnvios();
        emisor = new Emisor(colaEnvios);
        clientePartida = new ClienteTCP(colaEnvios, puertoEntrada, host);
        colaEnvios.agregarObservador(clientePartida);

        // receptor del servicio
        colaRecibos = new ColaRecibos();
        servidorPartida = new ServidorTCP(colaRecibos, puertoServidor);
        configuraciones.setEmisor(emisor);

        receptorPartida = new Receptor();
        receptorPartida.setCola(colaRecibos);

        receptorPartida.setReceptor(partidaComunicacion);
        colaRecibos.agregarObservador(receptorPartida);

        List<String> eventos = Arrays.asList(
                "JUGADA_REALIZADA",
                "TURNO_ACTUALIZADO",
                "INICIO_PARTIDA",
                "NUEVA_LINEA",
                "UNIRSE_PARTIDA",
                "ABANDONAR_PARTIDA",
                "CONFIGURAR_PARTIDA",
                "SOLICITAR_FINALIZAR_PARTIDA",
                "ACTUALIZAR_PUNTOS",
                "OBTENER_CONFIGURACIONES_PARTIDA",
                "CONFIRMAR_INICIO_PARTIDA",
                "SOLICITAR_INICIAR_PARTIDA",
                "REGISTRAR_JUGADOR"
        );

        PaqueteDTO solicitarConexion = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        solicitarConexion.setHost(host);
        solicitarConexion.setPuertoOrigen(puertoServidor);
        solicitarConexion.setPuertoDestino(puertoEntrada);

        PaqueteDTO registrarJugador = new PaqueteDTO(sesion, TipoEvento.REGISTRAR_JUGADOR.toString());
        registrarJugador.setHost(host);
        registrarJugador.setPuertoOrigen(puertoServidor);
        registrarJugador.setPuertoDestino(puertoEntrada);

        modelo = new ModeloPartida(partida);

        controlador = new ControladorPartida(modelo);

        // MVC de configuraciones
        ModeloArranque modeloConfig = new ModeloArranque(configuraciones);
        modeloConfig.setSesion(sesion);
        ControladorArranque controladorConfig = new ControladorArranque(controlador, modeloConfig);
        FrmSalaEspera vista = new FrmSalaEspera(modeloConfig, controladorConfig);
        configuraciones.agregarObservador(modeloConfig);
        modeloConfig.agregarObservadorConfiguraciones(vista);
        modeloConfig.agregarObservadorEventoInicio(controladorConfig);

        new Thread(() -> servidorPartida.iniciar()).start();
        emisor.enviarCambio(solicitarConexion);
        emisor.enviarCambio(registrarJugador);

//        controladorConfig.obtenerConfiguraciones();
    }

    /**
     * Metodo que inicializa una nueva partida con configuraciones brindadas por
     * el usuario.
     */
    public void iniciarPartida(List<Jugador> jugadores, int alto, int ancho, Jugador sesion) {

        if (jugadores.size() > NUMERO_JUGADORES) {
            throw new IllegalArgumentException("Se ha alcanzado el limite de jugadores");
        }

        List<JugadorDTO> jugadoresdto = mapper.toListaDTO(jugadores);

        partida.setJugadores(jugadores);
        partida.setTablero(alto, ancho);
        partida.setJugadorSesion(sesion);
        partida.setHost(host);
        partida.setPuertoOrigen(puertoServidor);
        partida.setPuertoDestino(puertoEntrada);

        partidaComunicacion.setPartida(partida);
        partida.setEmisor(emisor);

        IModeloJugadoresLectura imjl = modelo;
        IModeloTableroLectura imtl = modelo;
        FrmPartida frm = new FrmPartida(imjl, imtl, controlador);
        partida.agregarObservadorInicioJuego(modelo);
        partida.agregarObservadorJugadores(modelo);
        partida.agregarObservadorEventos(modelo);

        modelo.agregarObservadorJugadores(frm.getObservadorJugadores());
        modelo.agregarObservadorTablero(frm.getObservadorTablero());
        modelo.agregarObservadorInicioJuego(frm);

        PaqueteDTO solicitarTurnos = new PaqueteDTO(jugadoresdto, "SOLICITAR_TURNOS");
        solicitarTurnos.setHost(host);
        solicitarTurnos.setPuertoOrigen(puertoServidor);
        solicitarTurnos.setPuertoDestino(puertoEntrada);
        // emisor.enviarCambio(solicitarTurnos);

        //MVC de juego
        partida.setJugadorSesion(sesion);

        partida.inicioPartida();
    }
}
