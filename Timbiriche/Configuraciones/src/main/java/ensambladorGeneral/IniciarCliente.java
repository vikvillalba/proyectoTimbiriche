package ensambladorGeneral;

import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Entidades.TipoEvento;
import MVCConfiguracion.controlador.ControladorArranque;
import MVCConfiguracion.modelo.ModeloArranque;
import ModeloUnirsePartida.ReceptorUnirsePartida;
import ModeloUnirsePartida.UnirsePartida;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import java.util.Arrays;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;
import org.itson.presentacion.FrmMenuInicio;

/**
 *
 * @author Jack Murrieta
 */
public class IniciarCliente {

    // Configuración de red
    private static final String HOST_EVENTBUS = "localhost";
    private static final int PUERTO_EVENTBUS = 5555;    // Puerto del EventBus (config_bus.properties)
    private static final int PUERTO_CLIENTE = 6002;     // Puerto donde escucha este CLIENTE

    public static void main(String[] args) {

        ColaEnvios colaEnvios = new ColaEnvios();
        IEmisor emisor = new Emisor(colaEnvios);

        // ClienteTCP observa la cola y envía los paquetes por red
        ClienteTCP clienteTCP = new ClienteTCP(colaEnvios, PUERTO_EVENTBUS, HOST_EVENTBUS);
        colaEnvios.agregarObservador(clienteTCP);

        System.out.println("Eisor configurado para EventBus en " + HOST_EVENTBUS + ":" + PUERTO_EVENTBUS);

        ColaRecibos colaRecibos = new ColaRecibos();

        // ServidorTCP escucha en el puerto del CLIENTE
        ServidorTCP servidorTCP = new ServidorTCP(colaRecibos, PUERTO_CLIENTE);

        System.out.println("Servidor TCP configurado en puerto " + PUERTO_CLIENTE);

        UnirsePartida unirsePartida = new UnirsePartida();
        unirsePartida.setPuertoOrigen(PUERTO_CLIENTE);
        unirsePartida.setPuertoDestino(PUERTO_EVENTBUS);
        unirsePartida.setEmisorSolicitud(emisor);

        System.out.println("[✓] UnirsePartida creado");

        IReceptor receptorCliente = new ReceptorUnirsePartida(unirsePartida);
        unirsePartida.setReceptorSolicitud(receptorCliente);

        System.out.println("[✓] ReceptorSolicitudCliente configurado");

        Receptor receptor = new Receptor();
        receptor.setCola(colaRecibos);
        receptor.setReceptor(receptorCliente); // Cuando llegue un paquete, se envía a ReceptorSolicitudCliente
        colaRecibos.agregarObservador(receptor);

        System.out.println("[✓] Receptor genérico conectado");

        // Crear ModeloArranque
        ModeloArranque modeloArranque = new ModeloArranque(null, unirsePartida);

        // Crear ControladorArranque
        ControladorArranque controladorArranque = new ControladorArranque(null, null, modeloArranque);

        System.out.println("[✓] Modelo y Controlador creados");

        // Conectar UnirsePartida con ModeloArranque
        // UnirsePartida notifica a ModeloArranque cuando llega una respuesta
        unirsePartida.agregarNotificadorSolicitud(modeloArranque);
        unirsePartida.agregarNotificadorHostEncontrado(modeloArranque);

        System.out.println("[✓] UnirsePartida conectado con ModeloArranque");

        // El cliente se suscribe inicialmente solo a eventos de respuesta
        // Cuando se una a una sala, se suscribirá a EN_SALA_ESPERA y SOLICITAR_UNIRSE
        List<String> eventos = Arrays.asList(
                "RESPUESTA_HOST",        // Para obtener info del host
                "RESULTADO_CONSENSO"     // Para recibir resultado del consenso de votación
        );

        PaqueteDTO registroEventBus = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        registroEventBus.setHost(HOST_EVENTBUS);
        registroEventBus.setPuertoOrigen(PUERTO_CLIENTE);
        registroEventBus.setPuertoDestino(PUERTO_EVENTBUS);

        System.out.println("[✓] Enviando registro al EventBus: " + eventos);
        emisor.enviarCambio(registroEventBus);

        new Thread(() -> {
            System.out.println("[→] Servidor TCP iniciando...");
            servidorTCP.iniciar();
        }).start();

        System.out.println("[✓] Servidor TCP iniciado correctamente\n");

        // Crear el Frame de Menú de Inicio
        FrmMenuInicio frmMenuInicio = new FrmMenuInicio();

        // Configurar IP y puerto en la vista
        frmMenuInicio.setIp(HOST_EVENTBUS);
        frmMenuInicio.setPuerto(PUERTO_CLIENTE);

        // Conectar el controlador con la vista
        frmMenuInicio.setControlador(controladorArranque);

        // Registrar la vista como observador del modelo
        // Cuando llegue una respuesta, ModeloArranque notificará a FrmMenuInicio
        modeloArranque.agregarNotificador(frmMenuInicio);
        modeloArranque.agregarNotificadorHostUnirsePartida(frmMenuInicio);

        System.out.println("[✓] FrmMenuInicio creado y conectado");

        // Mostrar la interfaz gráfica
        java.awt.EventQueue.invokeLater(() -> {
            frmMenuInicio.setLocationRelativeTo(null);
            frmMenuInicio.setVisible(true);

        });
    }
}
