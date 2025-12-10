package ensambladorGeneral;

import DTO.JugadorConfigDTO;
import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Entidades.TipoEvento;
import MVCConfiguracion.controlador.ControladorArranque;
import MVCConfiguracion.modelo.ModeloArranque;
import MVCConfiguracion.vista.FrmSalaEspera;
import ModeloUnirsePartida.ReceptorUnirsePartida;
import ModeloUnirsePartida.UnirsePartida;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import objetosPresentables.JugadorConfig;
import objetosPresentables.TableroConfig;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class IniciarJugadorSalaEspera {

    // Configuración cargada desde archivo properties
    private static String HOST_EVENTBUS;
    private static int PUERTO_EVENTBUS;
    private static int PUERTO_JUGADOR;
    private static int PUERTO_SERVIDOR;
    private static int PUERTO_TURNOS;
    
    public static void main(String[] args) {

        // 1. CARGAR CONFIGURACIÓN DESDE config_partida1.properties
        cargarConfiguracion("config_partida1.properties");
        
        System.out.println("  INICIAR JUGADOR EN SALA DE ESPERA");
        System.out.println("Host EventBus: " + HOST_EVENTBUS);
        System.out.println("Puerto EventBus: " + PUERTO_EVENTBUS);
        System.out.println("Puerto Jugador: " + PUERTO_JUGADOR);
        System.out.println("Puerto Servidor: " + PUERTO_SERVIDOR);
        System.out.println("Puerto Turnos: " + PUERTO_TURNOS);

        // ═══════════════════════════════════════════════════════════════
        // 2. CONFIGURAR EMISOR (enviar mensajes al EventBus)
        // ═══════════════════════════════════════════════════════════════
        ColaEnvios colaEnvios = new ColaEnvios();
        IEmisor emisor = new Emisor(colaEnvios);

        // ClienteTCP observa la cola y envía los paquetes por red
        ClienteTCP clienteTCP = new ClienteTCP(colaEnvios, PUERTO_EVENTBUS, HOST_EVENTBUS);
        colaEnvios.agregarObservador(clienteTCP);
        
        System.out.println("Emisor configurado para EventBus en " + HOST_EVENTBUS + ":" + PUERTO_EVENTBUS);

        // CONFIGURAR RECEPTOR (recibir mensajes del EventBus)
        ColaRecibos colaRecibos = new ColaRecibos();

        // ServidorTCP escucha en el puerto del JUGADOR
        ServidorTCP servidorTCP = new ServidorTCP(colaRecibos, PUERTO_JUGADOR);
        
        System.out.println("Servidor TCP configurado en puerto " + PUERTO_JUGADOR);
        
        UnirsePartida unirsePartida = new UnirsePartida();
        unirsePartida.setPuertoOrigen(PUERTO_JUGADOR);
        unirsePartida.setPuertoDestino(PUERTO_EVENTBUS);
        unirsePartida.setEmisorSolicitud(emisor);
        
        System.out.println("UnirsePartida creado");
        
        IReceptor receptorJugador = new ReceptorUnirsePartida(unirsePartida);
        unirsePartida.setReceptorSolicitud(receptorJugador);
        
        System.out.println("ReceptorUnirsePartida configurado");
        
        Receptor receptor = new Receptor();
        receptor.setCola(colaRecibos);
        receptor.setReceptor(receptorJugador);
        colaRecibos.agregarObservador(receptor);
        
        System.out.println("Receptor genérico conectado");

        //CREAR MODELO Y CONTROLADOR
        ModeloArranque modeloArranque = new ModeloArranque(null, unirsePartida);
        ControladorArranque controladorArranque = new ControladorArranque(null, null, modeloArranque);
        
        System.out.println("Modelo y Controlador creados");

        // Conectar UnirsePartida con ModeloArranque
        unirsePartida.agregarNotificadorSolicitud(modeloArranque);
        unirsePartida.agregarNotificadorHostEncontrado(modeloArranque);
        System.out.println("UnirsePartida conectado con ModeloArranque");

        //CREAR JUGADOR
        JugadorConfigDTO jugadorDTO = new JugadorConfigDTO();
        jugadorDTO.setNombre("JUGADOR_1");
        jugadorDTO.setAvatar("tiburonMartillo");
        jugadorDTO.setColor("verde_pastel");
        jugadorDTO.setEsHost(false); //atributo ya no necesario 
        jugadorDTO.setIp(HOST_EVENTBUS);
        jugadorDTO.setPuerto(PUERTO_JUGADOR);

        //SUSCRIBIRSE A EVENTOS DEL EVENTBUS
        List<String> eventos = Arrays.asList(
                "EN_SALA_ESPERA", // Actualizaciones de la sala de espera
                "SOLICITAR_UNIRSE" // Notificaciones de nuevos jugadores
        // "RESULTADO_CONSENSO"     // puede ir logica de actaulizar jugadores en sala partida
        );
        
        PaqueteDTO registroEventBus = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        registroEventBus.setHost(HOST_EVENTBUS);
        registroEventBus.setPuertoOrigen(PUERTO_JUGADOR);
        registroEventBus.setPuertoDestino(PUERTO_EVENTBUS);
        
        System.out.println("Enviando registro al EventBus: " + eventos);
        emisor.enviarCambio(registroEventBus);

        // 8. INICIAR SERVIDOR TCP EN HILO SEPARADO
        new Thread(() -> {
            System.out.println("[→] Servidor TCP iniciando...");
            servidorTCP.iniciar();
        }).start();
        
        System.out.println("[✓] Servidor TCP iniciado correctamente\n");

        // 9. CREAR INTERFAZ GRÁFICA - SALA DE ESPERA
        java.awt.Image avatarImage = cargarAvatar(jugadorDTO.getAvatar());
        java.awt.Color colorAWT = obtenerColor(jugadorDTO.getColor());
        
        JugadorConfig jugadorConfig = new JugadorConfig(
                jugadorDTO.getNombre(),
                avatarImage,
                colorAWT,
                false, // si esta listo
                false // si es host
        );
        
        List<JugadorConfig> jugadoresInicial = Arrays.asList(jugadorConfig);
        
        TableroConfig tableroConfig = new TableroConfig(8, 8);

        // Crear el Frame de Sala de Espera
        FrmSalaEspera frmSalaEspera = new FrmSalaEspera(jugadoresInicial, tableroConfig, jugadorConfig);

        // Conectar el controlador con la vista
        frmSalaEspera.setControlador(controladorArranque);

        // Registrar la vista como observador del modelo
        modeloArranque.agregarNotificador(frmSalaEspera);
        unirsePartida.agregarNotificadorConsenso(frmSalaEspera);
        
        System.out.println("[✓] FrmSalaEspera creado y conectado");

        // Mostrar la interfaz gráfica
        java.awt.EventQueue.invokeLater(() -> {
            frmSalaEspera.setLocationRelativeTo(null);
            frmSalaEspera.setVisible(true);
        });
        
        System.out.println("  JUGADOR INICIADO CORRECTAMENTE");
    }

    /**
     * Carga la configuración desde el archivo properties especificado. Busca primero en el classpath, luego en el sistema de archivos.
     *
     * @param nombreArchivo Nombre del archivo properties (ej: "config_partida1.properties")
     */
    private static void cargarConfiguracion(String nombreArchivo) {
        Properties prop = new Properties();
        
        try {
            // Intentar cargar desde classpath (src/main/resources)
            InputStream inputStream = IniciarJugadorSalaEspera.class
                    .getClassLoader()
                    .getResourceAsStream(nombreArchivo);
            
            if (inputStream != null) {
                prop.load(inputStream);
                System.out.println("Configuración cargada desde classpath: " + nombreArchivo);
            } else {
                // Si no está en classpath, intentar cargar desde archivo
                FileInputStream fileInput = new FileInputStream(nombreArchivo);
                prop.load(fileInput);
                fileInput.close();
                System.out.println("Configuración cargada desde archivo: " + nombreArchivo);
            }

            // Leer valores del archivo
            HOST_EVENTBUS = prop.getProperty("host", "localhost");
            PUERTO_EVENTBUS = Integer.parseInt(prop.getProperty("puerto.entrada", "5555"));  // Puerto donde ESCUCHA el EventBus
            PUERTO_JUGADOR = Integer.parseInt(prop.getProperty("puerto.servidor", "6000"));  // Puerto donde escucha este JUGADOR
            PUERTO_SERVIDOR = Integer.parseInt(prop.getProperty("puerto.servidor", "6000"));
            PUERTO_TURNOS = Integer.parseInt(prop.getProperty("puerto.turnos", "6001"));
            
        } catch (IOException | NumberFormatException e) {
            System.err.println("[ERROR] No se pudo cargar la configuración desde " + nombreArchivo);
            System.err.println("[ERROR] " + e.getMessage());
            System.err.println("[WARN] Usando valores por defecto");

            // Valores por defecto si falla la carga
            HOST_EVENTBUS = "localhost";
            PUERTO_EVENTBUS = 5555;  // Puerto de ENTRADA del EventBus
            PUERTO_JUGADOR = 8000;   // Puerto donde escucha este JUGADOR
            PUERTO_SERVIDOR = 6000;
            PUERTO_TURNOS = 6001;
        }
    }

    /**
     * Carga la imagen del avatar desde los recursos.
     *
     * @param nombreAvatar Nombre del avatar (ej: "tiburonMartillo")
     * @return Image del avatar o null si no se encuentra
     */
    private static java.awt.Image cargarAvatar(String nombreAvatar) {
        try {
            String nombreArchivo = nombreAvatar + ".png";
            java.net.URL url = IniciarJugadorSalaEspera.class.getResource("/avatares/" + nombreArchivo);
            
            if (url != null) {
                System.out.println("[✓] Avatar cargado: " + nombreArchivo);
                return new javax.swing.ImageIcon(url).getImage();
            } else {
                System.out.println("[WARN] No se encontró la imagen del avatar: " + nombreArchivo);
                return null;
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Error al cargar avatar: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convierte un string de color a objeto Color de AWT.
     *
     * @param nombreColor Nombre del color (ej: "verde_pastel")
     * @return Color de AWT
     */
    private static java.awt.Color obtenerColor(String nombreColor) {
        switch (nombreColor.toLowerCase()) {
            case "rojo_pastel":
                return new java.awt.Color(220, 20, 60);
            case "azul_pastel":
                return new java.awt.Color(135, 206, 235);
            case "verde_pastel":
                return new java.awt.Color(143, 188, 139);
            case "amarillo_pastel":
                return new java.awt.Color(242, 201, 104);
            case "magenta":
                return new java.awt.Color(201, 91, 170);
            case "naranja_pastel":
                return new java.awt.Color(240, 120, 77);
            case "rosa_pastel":
                return new java.awt.Color(247, 163, 176);
            case "azul_marino":
                return new java.awt.Color(49, 49, 125);
            case "moras":
                return new java.awt.Color(147, 112, 219);
            default:
                System.out.println("[WARN] Color no reconocido: " + nombreColor + ", usando verde por defecto");
                return new java.awt.Color(143, 188, 139); // verde_pastel por defecto
        }
    }
}
