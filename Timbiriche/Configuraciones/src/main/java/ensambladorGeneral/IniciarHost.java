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
import java.util.Arrays;
import java.util.List;
import objetosPresentables.JugadorConfig;
import objetosPresentables.TableroConfig;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 * Main para iniciar el juego como HOST con interfaz gráfica.
 *
 * @author Jack Murrieta
 */
public class IniciarHost {

    // Configuración de red
    private static final String HOST_EVENTBUS = "localhost";
    private static final int PUERTO_EVENTBUS = 5555;    // Puerto del EventBus (config_bus.properties)
    private static final int PUERTO_HOST = 6001;         // Puerto donde escucha este HOST

    public static void main(String[] args) {

        ColaEnvios colaEnvios = new ColaEnvios();
        IEmisor emisor = new Emisor(colaEnvios);

        // ClienteTCP observa la cola y envía los paquetes por red
        ClienteTCP clienteTCP = new ClienteTCP(colaEnvios, PUERTO_EVENTBUS, HOST_EVENTBUS);
        colaEnvios.agregarObservador(clienteTCP);

        System.out.println("Emisor configurado para EventBus en " + HOST_EVENTBUS + ":" + PUERTO_EVENTBUS);

        ColaRecibos colaRecibos = new ColaRecibos();

        // ServidorTCP escucha en el puerto del HOST
        ServidorTCP servidorTCP = new ServidorTCP(colaRecibos, PUERTO_HOST);

        System.out.println("Servidor TCP configurado en puerto " + PUERTO_HOST);

        // ═══════════════════════════════════════════════════════════════
        // 3. CREAR UnirsePartida (lógica de negocio)
        // ═══════════════════════════════════════════════════════════════
        UnirsePartida unirsePartida = new UnirsePartida();
        unirsePartida.setPuertoOrigen(PUERTO_HOST);
        unirsePartida.setPuertoDestino(PUERTO_EVENTBUS);
        unirsePartida.setEmisorSolicitud(emisor);

        System.out.println("UnirsePartida creado");

        IReceptor receptorHost = new ReceptorUnirsePartida(unirsePartida);
        unirsePartida.setReceptorSolicitud(receptorHost);

        System.out.println("ReceptorSolicitudHost configurado");

        Receptor receptor = new Receptor();
        receptor.setCola(colaRecibos);
        receptor.setReceptor(receptorHost); // Cuando llegue un paquete, se envía a ReceptorSolicitudHost
        colaRecibos.agregarObservador(receptor);

        // Crear ModeloArranque (necesita ConfiguracionesFachada null por ahora)
        ModeloArranque modeloArranque = new ModeloArranque(null, unirsePartida);

        // Crear ControladorArranque
        ControladorArranque controladorArranque = new ControladorArranque(null, null, modeloArranque);

        System.out.println("Modelo y Controlador creados");

        // Conectar UnirsePartida con ModeloArranque
        // UnirsePartida notifica a ModeloArranque cuando llega una solicitud
        unirsePartida.agregarNotificadorSolicitud(modeloArranque);
        // HOST no busca hosts - solo recibe solicitudes de otros jugadores
        // NOTA: El notificador de consenso se registra DESPUÉS de crear FrmSalaEspera (línea ~150)

        System.out.println("UnirsePartida conectado con ModeloArranque");

        JugadorConfigDTO jugadorHostDTO = new JugadorConfigDTO();
        jugadorHostDTO.setNombre("HOST_PLAYER");
        jugadorHostDTO.setAvatar("tiburonBallena");
        jugadorHostDTO.setColor("azul_pastel");
        jugadorHostDTO.setIp(HOST_EVENTBUS);
        jugadorHostDTO.setPuerto(PUERTO_HOST);

        // Configurar el host en UnirsePartida
        // El host se suscribe a EN_SALA_ESPERA, SOLICITAR_UNIRSE y CONSENSO_FINALIZADO
        List<String> eventos = Arrays.asList(
                "EN_SALA_ESPERA",       // Indica que está en sala de espera
                "SOLICITAR_UNIRSE",     // Recibe solicitudes de nuevos jugadores
                "CONSENSO_FINALIZADO"   // Recibe notificaciones cuando el consenso termina
        );

        PaqueteDTO registroEventBus = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        registroEventBus.setHost(HOST_EVENTBUS);
        registroEventBus.setPuertoOrigen(PUERTO_HOST);
        registroEventBus.setPuertoDestino(PUERTO_EVENTBUS);

        emisor.enviarCambio(registroEventBus);

        new Thread(() -> {
            System.out.println("[→] Servidor TCP iniciando...");
            servidorTCP.iniciar();
        }).start();

        System.out.println("[✓] Servidor TCP iniciado correctamente\n");

        // Crear lista de jugadores inicial (solo el HOST)
        // Convertir avatar (String) a Image y color (String) a Color
        java.awt.Image avatarImage = cargarAvatar(jugadorHostDTO.getAvatar());
        java.awt.Color colorAWT = obtenerColor(jugadorHostDTO.getColor());

        JugadorConfig jugadorHostConfig = new JugadorConfig(
                jugadorHostDTO.getNombre(),
                avatarImage,
                colorAWT,
                true, // listo
                true // esHost
        );

        List<JugadorConfig> jugadoresInicial = Arrays.asList(jugadorHostConfig);

        // Crear configuración del tablero (ejemplo)
        TableroConfig tableroConfig = new TableroConfig(8, 8);

        // Crear el Frame de Sala de Espera
        FrmSalaEspera frmSalaEspera = new FrmSalaEspera(
                jugadoresInicial,
                tableroConfig,
                jugadorHostConfig
        );

        // Conectar el controlador con la vista
        frmSalaEspera.setControlador(controladorArranque);

        // Registrar la vista como observador del modelo
        // Cuando llegue una solicitud, ModeloArranque notificará a FrmSalaEspera
        modeloArranque.agregarNotificador(frmSalaEspera);

        // ✅ LAZY REGISTRATION: Registrar notificador de consenso DESPUÉS de crear FrmSalaEspera
        // Cuando el consenso finalice, UnirsePartida notificará directamente a FrmSalaEspera
        unirsePartida.agregarNotificadorConsenso(frmSalaEspera);

        System.out.println("[✓] FrmSalaEspera creado y conectado");
        System.out.println("[✓] Notificador de consenso registrado");

        // Mostrar la interfaz gráfica
        java.awt.EventQueue.invokeLater(() -> {
            frmSalaEspera.setLocationRelativeTo(null);
            frmSalaEspera.setVisible(true);

        });
    }

    /**
     * Carga la imagen del avatar desde los recursos.
     *
     * @param nombreAvatar Nombre del avatar (ej: "tiburon_ballena")
     * @return Image del avatar o null si no se encuentra
     */
    private static java.awt.Image cargarAvatar(String nombreAvatar) {
        try {
            // Convertir de snake_case a camelCase
            // "tiburon_ballena" -> "tiburonBallena"
            String nombreArchivo = nombreAvatar + ".png";

            java.net.URL url = IniciarHost.class.getResource("/avatares/" + nombreArchivo);

            if (url != null) {
                System.out.println("Avatar cargado: " + nombreArchivo);
                return new javax.swing.ImageIcon(url).getImage();
            } else {
                System.out.println("No se encontró la imagen del avatar: " + nombreArchivo);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar avatar: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convierte un string de color a objeto Color de AWT.
     *
     * @param nombreColor Nombre del color (ej: "azul_pastel")
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
                System.out.println("[WARN] Color no reconocido: " + nombreColor + ", usando azul por defecto");
                return new java.awt.Color(135, 206, 235); // azul_pastel por defecto
        }
    }
}
