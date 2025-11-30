package MVCConfiguracion.modelo;

import ConfiguracionesFachada.ConfiguracionesFachada;
import DTO.JugadorSolicitanteDTO;
import MVCConfiguracion.observer.INotificadorUnirsePartida;
import MVCConfiguracion.observer.IPublicadorUnirsePartida;
import MVCConfiguracion.observer.ObservableConfiguraciones;
import MVCConfiguracion.observer.ObservadorConfiguraciones;
import MVCConfiguracion.observer.ObservadorEventoInicio;
import ModeloUnirsePartida.INotificadorSolicitud;
import ModeloUnirsePartida.IUnirsePartida;
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
public class ModeloArranque implements IModeloArranqueEscritura, IModeloArranqueLectura, ObservableConfiguraciones, IPublicadorUnirsePartida, INotificadorSolicitud {

    private ObservadorConfiguraciones observadorConfiguraciones;
    private ConfiguracionesFachada configuracionesPartida;
    private static final Map<String, Color> COLORES = new HashMap<>();
    private static final Map<String, Image> AVATARES = new HashMap<>();

    //CU_UnirsePartida
    //modelo unirse partida LOGICA
    private IUnirsePartida unirsePartida;

    //lista de los frms Notificados UNIRSE PARTIDA
    private List<INotificadorUnirsePartida> notificadosUnirsePartida = new ArrayList<>();

    //Solicitud de UnirsePartida
    private SolicitudUnirse solicitud;

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
        // Obtener la solicitud actual creada
        solicitud = obtenerSolicitud();
        //si la solicitud es null crear una por primera vez
        if (solicitud == null) {
            //crear la solicitud en unirsePartida 
            solicitud = unirsePartida.crearSolicitud(jugadorsolicitante);
            //enviar solicitud
            unirsePartida.enviarSolicitudJugadorHost(solicitud);
        }

        //cuando vaya a querer enviarla otra vez
        unirsePartida.enviarSolicitudJugadorHost(solicitud);

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
                unirse.enviarRespuestaSolicitud(solicitud);

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
    public void notificar() {
        for (INotificadorUnirsePartida notificado : notificadosUnirsePartida) {
            //se le puede pasar un param
            notificado.actualizar();

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
     * Método llamado cuando se recibe una actualización de solicitud desde UnirsePartida.
     * Determina qué diálogo mostrar según el estado de la solicitud.
     *
     * @param solicitud La solicitud actualizada
     */
    @Override
    public void actualizar(SolicitudUnirse solicitud) {
        // Actualizar la solicitud en el modelo
        this.solicitud = solicitud;

        System.out.println("ModeloArranque.actualizar() - Estado: " + (solicitud.isSolicitudEstado() ? "ACEPTADA" : "RECHAZADA"));

        // Notificar a los diálogos registrados para que actualicen su vista
        // Los diálogos decidirán si mostrarse según su propósito:
        // DlglicitudHostSe muestra cuando llega una solicitud nueva (lado HOST)
        // DlgEnviarSolicitudSe muestra cuando la solicitud es rechazada (lado CLIENTE)
        notificar();
    }

}
