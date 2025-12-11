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

    /**
     * Metodo que inicializa una nueva partida con configuraciones brindadas por el usuario. Conecta la Partida con UnirsePartida si se proporciona ModeloArranque.
     *
     * @param jugadores Lista de jugadores que participarán en la partida
     * @param alto Alto del tablero
     * @param ancho Ancho del tablero
     * @param sesion Jugador de la sesión actual
     * @param modeloArranque ModeloArranque para conectar la Partida (puede ser null si no es HOST)
     */
    public void iniciarPartida(List<Jugador> jugadores, int alto, int ancho, Jugador sesion, MVCConfiguracion.modelo.ModeloArranque modeloArranque) {

        PartidaComunicacion partidaComunicacion = new PartidaComunicacion();
        if (jugadores.size() > NUMERO_JUGADORES) {
            throw new IllegalArgumentException("Se ha alcanzado el limite de jugadores");
        }

        List<JugadorDTO> jugadoresdto = mapper.toListaDTO(jugadores);

        Partida partida = new Partida(jugadores, alto, ancho);
        partida.setHost(host);
        partida.setPuertoOrigen(puertoServidor);
        partida.setPuertoDestino(puertoEntrada);
        partida.setMaxJugadores(NUMERO_JUGADORES);

        // Conectar Partida con UnirsePartida si es HOST (modeloArranque != null)
        if (modeloArranque != null) {
            modeloArranque.setPartida(partida);
        }

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

        receptorPartida.setReceptor(partidaComunicacion);
        partidaComunicacion.setPartida(partida);
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
                "ACTUALIZAR_PUNTOS"
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
        partida.agregarObservadorJugadores(modelo);
        partida.agregarObservadorEventos(modelo);

        modelo.agregarObservadorJugadores(frm.getObservadorJugadores());
        modelo.agregarObservadorTablero(frm.getObservadorTablero());
        modelo.agregarObservadorInicioJuego(frm);
        partida.inicioPartida();
    }

    /**
     * Versión simplificada de iniciarPartida sin conexión a UnirsePartida. Útil para testing o cuando no se necesita manejo de solicitudes.
     *
     * @param jugadores Lista de jugadores que participarán en la partida
     * @param alto Alto del tablero
     * @param ancho Ancho del tablero
     * @param sesion Jugador de la sesión actual
     */
    public void iniciarPartida(List<Jugador> jugadores, int alto, int ancho, Jugador sesion) {
        iniciarPartida(jugadores, alto, ancho, sesion, null);
    }
}
