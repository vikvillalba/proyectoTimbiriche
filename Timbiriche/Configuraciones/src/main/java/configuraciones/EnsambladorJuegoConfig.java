package configuraciones;


import Configuraciones.ConfiguracionesPartida;
import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
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

    private String host;
    private int puertoEntrada; // Puerto de escucha local del cliente 
    private int puertoServicio; // Puerto del Event Bus 


    public EnsambladorJuegoConfig(String configFile) throws IOException {
        Properties props = new Properties();

        InputStream input = getClass().getClassLoader().getResourceAsStream(configFile);
        
        if (input == null) {
            throw new IOException("No se encontro el archivo de configuracion: " + configFile);
        }
        props.load(input);

        this.host = props.getProperty("host");
        this.puertoEntrada = Integer.parseInt(props.getProperty("puerto.cliente.entrada"));
        this.puertoServicio = Integer.parseInt(props.getProperty("puerto.bus.entrada")); 
        
        System.out.println("[Configuración] Host: " + this.host + ", Puerto Local: " + this.puertoEntrada + ", Puerto Bus: " + this.puertoServicio);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {

                EnsambladorJuegoConfig ensamblador = new EnsambladorJuegoConfig("config_cliente.properties");
                ensamblador.ensamblar();
            } catch (Exception e) {
                System.err.println("Fallo crítico en el ensamblaje: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void ensamblar() throws Exception {

        partidaUnica = new Partida(); 
        partidaUnica.setHost(host);
        partidaUnica.setPuertoOrigen(puertoEntrada); // El puerto de escucha local
        partidaUnica.setPuertoDestino(puertoServicio); // El puerto al que se envían los datos (Bus)
        
        partidaUnica.setJugadorSesion(jugadorSesionLocal);
        
        ColaEnvios colaEnvios = new ColaEnvios();
        IEmisor emisor = new Emisor(colaEnvios);
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
        
    
        ModeloPartida modeloJuego = new ModeloPartida(partidaUnica);
        ControladorPartida controladorJuego = new ControladorPartida((IModeloPartidaEscritura) modeloJuego);
        
        FrmPartida frmPartida = new FrmPartida((IModeloJugadoresLectura) modeloJuego, (IModeloTableroLectura) modeloJuego, controladorJuego);
        
    
        partidaUnica.agregarObservadorInicioJuego(modeloJuego);
        partidaUnica.agregarObservadorJugadores(modeloJuego);
        partidaUnica.agregarObservadorEventos(modeloJuego);

        modeloJuego.agregarObservadorInicioJuego(frmPartida);
        modeloJuego.agregarObservadorJugadores(frmPartida.getObservadorJugadores());
        modeloJuego.agregarObservadorTablero(frmPartida.getObservadorTablero());
        

        new Thread(() -> servidorPartida.iniciar()).start();
        

        iniciarConexion(emisor);


        frmConfigurarPartida frmConfig = new frmConfigurarPartida(controladorArranque);
        frmConfig.setVisible(true);
        
        
    }

    private void iniciarConexion(IEmisor emisor) {
        List<String> eventos = Arrays.asList(
             "JUGADA_REALIZADA", "TURNO_ACTUALIZADO", "INICIO_PARTIDA",
             "NUEVA_LINEA", "UNIRSE_PARTIDA", "ABANDONAR_PARTIDA", 
             "CONFIGURAR_PARTIDA", "SOLICITAR_FINALIZAR_PARTIDA", "ACTUALIZAR_PUNTOS"
        );

        PaqueteDTO solicitarConexion = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        solicitarConexion.setHost(host);
        solicitarConexion.setPuertoOrigen(puertoEntrada);
        solicitarConexion.setPuertoDestino(puertoServicio);
        emisor.enviarCambio(solicitarConexion);
    }
    
    public static void setJugadorSesion(Jugador jugador) {
        
        jugadorSesionLocal = jugador; 
    }
}