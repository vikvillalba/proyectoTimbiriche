package MVCConfiguracion.modelo;

import ConfiguracionesFachada.ConfiguracionesFachada;
import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import Fachada.Partida;
import MVCConfiguracion.observer.INotificadorUnirsePartida;
import MVCConfiguracion.observer.IPublicadorUnirsePartida;
import MVCConfiguracion.observer.ObservableConfiguraciones;
import MVCConfiguracion.observer.ObservadorConfiguraciones;
import MVCConfiguracion.observer.ObservadorEventoInicio;
import ModeloUnirsePartida.IUnirsePartida;
import ModeloUnirsePartida.Observadores.INotificadorHostEncontrado;
import ModeloUnirsePartida.Observadores.INotificadorSolicitud;
import SolicitudEntity.SolicitudUnirse;
import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import objetosPresentables.JugadorConfig;
import objetosPresentables.PartidaPresentable;

/**
 * Modelo de arranque que maneja la configuración de la partida y el proceso de unirse a partidas existentes.
 *
 * @author victoria
 */
public class ModeloArranque implements IModeloArranqueEscritura, IModeloArranqueLectura, ObservableConfiguraciones,
        IPublicadorUnirsePartida, INotificadorSolicitud, INotificadorHostEncontrado, IPublicadorHostUnirsePartida {

    private ObservadorConfiguraciones observadorConfiguraciones;
    private ConfiguracionesFachada configuracionesPartida;
    private static final Map<String, Color> COLORES = new HashMap<>();
    private static final Map<String, Image> AVATARES = new HashMap<>();

    //CU_UnirsePartida
    //modelo unirse partida LOGICA
    private IUnirsePartida unirsePartida;

    //lista de los frms Notificados UNIRSE PARTIDA
    private List<INotificadorUnirsePartida> notificadosUnirsePartida = new ArrayList<>();

    private INotificadorHostUnirsePartida frmMenuInicio;

    //Solicitud de UnirsePartida
    private SolicitudUnirse solicitud;

    private JugadorConfigDTO jugadorHost;

    public ModeloArranque(ConfiguracionesFachada configuracionesPartida, IUnirsePartida unirsePartida) {
        this.configuracionesPartida = configuracionesPartida;
        this.unirsePartida = unirsePartida;
    }

    static {
        // Mapa con los nombres que se usan
        COLORES.put("rojo_pastel", new Color(220, 20, 60));
        COLORES.put("azul_pastel", new Color(135, 206, 235));
        COLORES.put("verde_pastel", new Color(143, 188, 139));
        COLORES.put("amarillo_pastel", new Color(242, 201, 104));
        COLORES.put("magenta", new Color(201, 91, 170));
        COLORES.put("naranja_pastel", new Color(240, 120, 77));
        COLORES.put("rosa_pastel", new Color(247, 163, 176));
        COLORES.put("azul_marino", new Color(49, 49, 125));
        COLORES.put("moras", new Color(147, 112, 219));

        // mapa con los avatares disponibles
        AVATARES.put("tiburon_ballena", cargarAvatar("tiburonBallena.png"));
        AVATARES.put("tiburon_blanco", cargarAvatar("tiburonBlanco.png"));
        AVATARES.put("tiburon_martillo", cargarAvatar("tiburonMartillo.png"));
        AVATARES.put("tiburon_smile_blue", cargarAvatar("tiburonSmileBlue.png"));
        AVATARES.put("tiburon_smile_gray", cargarAvatar("tiburonSmileGray.png"));
        AVATARES.put("tiburon_jump_blue", cargarAvatar("tiburonJumpBlue.png"));
        AVATARES.put("tiburon_jump_gray", cargarAvatar("tiburonJumpGray.png"));
        AVATARES.put("tiburon_still_blue", cargarAvatar("tiburonStillBlue.png"));
        AVATARES.put("tiburon_still_gray", cargarAvatar("tiburonStillGray.png"));
    }

    private static Image cargarAvatar(String nombreArchivo) {
        URL url = ModeloArranque.class.getResource("/avatares/" + nombreArchivo);
        if (url != null) {
            return new ImageIcon(url).getImage();
        } else {
            System.out.println("No se encontró la imagen: " + nombreArchivo);
            return null;
        }
    }

    @Override
    public void iniciarConexion(List<JugadorConfig> jugadores, int altoTablero, int anchoTablero, JugadorConfig sesion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void solicitarInicioConexion(JugadorConfig jugador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void confirmarInicioJuego(JugadorConfig jugador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PartidaPresentable getConfiguracionesPartida() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void agregarObservadorConfiguraciones(ObservadorConfiguraciones ob) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void agregarObservadorEventoInicio(ObservadorEventoInicio ob) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void notificarInicioPartida(PartidaPresentable partida) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void notificarConfiguraciones(PartidaPresentable partida) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //CU UNIRSE PARTIDA
    /**
     * Crea un JugadorSolicitanteDTO y genera una solicitud para unirse a la partida.
     *
     * @param ip Dirección IP del jugador solicitante
     * @param puerto Puerto del jugador solicitante
     * @return JugadorSolicitanteDTO creado
     */
    @Override
    public JugadorSolicitanteDTO crearJugadorSolicitante(String ip, int puerto) {

        // Crear el JugadorSolicitanteDTO
        JugadorSolicitanteDTO jugadorSolicitante = new JugadorSolicitanteDTO(ip, puerto);
        return jugadorSolicitante;

    }

    @Override
    public void enviarSolicitud(JugadorSolicitanteDTO jugadorsolicitante) {
        if (jugadorsolicitante == null) {
            throw new IllegalArgumentException("El jugador solicitante no puede ser nulo");
        }

        // Establecer el jugador solicitante
        unirsePartida.setJugadorSolicitante(jugadorsolicitante);

        // Obtener la solicitud actual si existe
        solicitud = obtenerSolicitud();

        // Si no existe solicitud, crear una nueva
        if (solicitud == null) {
            solicitud = unirsePartida.crearSolicitud(jugadorsolicitante);
        }

        // Enviar la solicitud al host
        unirsePartida.enviarSolicitudSalaEspera(solicitud);
    }

    /**
     * Obtiene la solicitud actual creada.
     *
     * @return SolicitudUnirse actual o null si no existe
     */
    @Override
    public SolicitudUnirse obtenerSolicitud() {
        solicitud = unirsePartida.getSolicitudActual();

        if (solicitud == null) {
            return null;
        }

        return solicitud;
    }

    @Override
    public void setEstadoSolicitud(boolean estadoSolicitud) {
        // Si la solicitud fue rechazada, establecer el tipo de rechazo ANTES de notificar
        if (solicitud != null && !estadoSolicitud) {
            solicitud.setTipoRechazo("RECHAZADO_POR_HOST");
        }

        // Cambiar el estado de la solicitud
        unirsePartida.cambiarEstadoSolicitud(solicitud, estadoSolicitud);

        // Obtener la solicitud actualizada
        solicitud = obtenerSolicitud();

        // Enviar la respuesta al solicitante por EventBus
        if (solicitud != null) {
            enviarRespuestaASolicitante(solicitud);
        }
    }

    /**
     * Envía la respuesta de la solicitud al solicitante a través del EventBus. Este método es privado y se usa internamente por setEstadoSolicitud.
     *
     * @param solicitud La solicitud con el estado actualizado
     */
    private void enviarRespuestaASolicitante(SolicitudUnirse solicitud) {
        try {
            // Verificar que UnirsePartida tenga el método
            if (unirsePartida instanceof ModeloUnirsePartida.UnirsePartida) {
                ModeloUnirsePartida.UnirsePartida unirse = (ModeloUnirsePartida.UnirsePartida) unirsePartida;
                unirse.enviarVotoSolicitud(solicitud);

                String estado = solicitud.isSolicitudEstado() ? "ACEPTADA" : "RECHAZADA";
                System.out.println("✓ Respuesta " + estado + " enviada al solicitante");
            }
        } catch (Exception e) {
            System.err.println("ERROR al enviar respuesta al solicitante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //PUBLICADOR METODOS
    @Override
    public void agregarNotificador(INotificadorUnirsePartida notificador) {
        notificadosUnirsePartida.add(notificador);
    }

    @Override
    public void notificar(SolicitudUnirse solicitud) {
        for (INotificadorUnirsePartida notificado : notificadosUnirsePartida) {
            //se le puede pasar un param
            notificado.actualizarSolicitudUnirse(solicitud);

        }

    }

    /**
     * Establece la solicitud actual. Este método es usado por los receptores para actualizar la solicitud.
     *
     * @param solicitud La solicitud recibida
     */
    @Override
    public void setSolicitudActual(SolicitudUnirse solicitud) {
        this.solicitud = solicitud;
        System.out.println("Solicitud actualizada en el modelo");
    }

    /**
     * Método llamado cuando se recibe una actualización de solicitud desde UnirsePartida. Determina qué diálogo mostrar según el estado de la solicitud.
     *
     * @param solicitud La solicitud actualizada
     */
    @Override
    public void actualizar(SolicitudUnirse solicitud) {
        // Actualizar la solicitud en el modelo
        this.solicitud = solicitud;
        
        //envia eventos que ahora escucha el jugadr que se unioo
        if(solicitud.isSolicitudEstado()){
            unirsePartida.suscribirseASalaEspera();
        }

        // Notificar a los diálogos registrados para que actualicen su vista
        // Los diálogos decidirán si mostrarse según su propósito:
        // DlglicitudHostSe muestra cuando llega una solicitud nueva (lado HOST)
        // DlgEnviarSolicitudSe muestra cuando la solicitud es rechazada (lado CLIENTE)
        notificar(solicitud);
    }

    @Override
    public void volverEnviarSolicitud(SolicitudUnirse solicitud) {
        this.solicitud = solicitud;
        this.solicitud.setSolicitudEstado(false);
        this.solicitud.setTipoRechazo("");
        unirsePartida.enviarSolicitudSalaEspera(solicitud);
    }

    /**
     * Conecta la PartidaPresentable con el módulo UnirsePartida SOLO SI este ModeloArranque pertenece a un HOST.
     *
     * @param partida La partida creada por el host
     */
    public void setPartida(Partida partida) {

        if (partida == null) {
            System.err.println("No se puede conectar una partida nula.");
            return;
        }

        if (unirsePartida != null) {

            try {
                unirsePartida.setPartida(partida);

            } catch (Exception e) {
                System.err.println("ERROR al conectar partida a UnirsePartida: " + e.getMessage());
            }

        } else {
            System.err.println("UnirsePartida es null. No se pudo establecer la conexión.");
        }
    }

    @Override
    public void buscarHostPartida(JugadorSolicitanteDTO jugadorSolicitante) {
        unirsePartida.solicitarHost(jugadorSolicitante);
    }

    /**
     * Cuando se encuentra el host de la partida, convierte el DTO y notifica a la vista. Implementa INotificadorHostEncontrado.
     *
     * @param jugador El jugador host encontrado (puede ser null)
     */
    @Override
    public void actualizar(JugadorConfigDTO jugador) {

        this.jugadorHost = jugador;
        JugadorConfig jugadorHostEncontrado = mapearJugadorConfig(jugador);

        // Notificar a FrmMenuInicio
        notificar(jugadorHostEncontrado);
    }

    //mappear JugadorConfigDTO a jugadrConfig 
    private JugadorConfig mapearJugadorConfig(JugadorConfigDTO dto) {
        if (dto == null) {
            return null;
        }

        JugadorConfig jugador = new JugadorConfig();
        jugador.setNombre(dto.getNombre());
        jugador.setAvatar(AVATARES.get(dto.getAvatar()));
        jugador.setColor(COLORES.get(dto.getColor()));

        jugador.setEsHost(dto.isEsHost());

        return jugador;
    }

    /**
     * Registra el notificador que recibirá actualizaciones cuando se encuentre un host. Implementa IPublicadorHostUnirsePartida.
     *
     * @param notificador El notificador a registrar (FrmMenuInicio)
     */
    @Override
    public void agregarNotificadorHostUnirsePartida(INotificadorHostUnirsePartida notificador) {
        System.out.println("[ModeloArranque] Registrando notificador para host encontrado: " + (notificador != null ? notificador.getClass().getSimpleName() : "NULL"));
        this.frmMenuInicio = notificador;
    }

    /**
     * Notifica a FrmMenuInicio que se encontró un host. Implementa IPublicadorHostUnirsePartida.
     *
     * @param jugadorHost El jugador host encontrado (puede ser null)
     */
    @Override
    public void notificar(JugadorConfig jugadorHost) {
        System.out.println("[ModeloArranque] notificar(JugadorConfig) - Notificando a FrmMenuInicio. Host: " + (jugadorHost != null ? jugadorHost.getNombre() : "NULL"));

        if (frmMenuInicio == null) {
            System.err.println("[ERROR] frmMenuInicio es NULL - no se puede notificar");
            return;
        }

        frmMenuInicio.actualizar(jugadorHost);
    }
}
