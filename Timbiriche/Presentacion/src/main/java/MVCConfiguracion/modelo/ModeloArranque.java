package MVCConfiguracion.modelo;

import ConfiguracionesFachada.ConfiguracionesFachada;
import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import Fachada.Partida;
import MVCConfiguracion.UnirsePartida.Observers.INotificadorUnirsePartida;
import MVCConfiguracion.UnirsePartida.Observers.IPublicadorUnirsePartida;
import MVCConfiguracion.observer.ObservadorConfiguraciones;
import ModeloUnirsePartida.Fachada.IUnirsePartidaFachada;
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

/**
 * Modelo de arranque que maneja la configuración de la partida y el proceso de unirse a partidas existentes.
 *
 * @author victoria
 */
public class ModeloArranque implements IModeloArranqueEscritura, IModeloArranqueLectura,
        IPublicadorUnirsePartida, INotificadorSolicitud {

    private ObservadorConfiguraciones observadorConfiguraciones;
    private ConfiguracionesFachada configuracionesPartida;
    private static final Map<String, Color> COLORES = new HashMap<>();
    private static final Map<String, Image> AVATARES = new HashMap<>();

    //CU_UnirsePartida
    private IUnirsePartidaFachada unirsePartidaFachada;

    //lista de los frms Notificados UNIRSE PARTIDA
    private List<INotificadorUnirsePartida> notificadosUnirsePartida = new ArrayList<>();

    //Solicitud de UnirsePartida
    private SolicitudUnirse solicitud;

    private JugadorConfigDTO jugadorHost;

    public ModeloArranque(ConfiguracionesFachada configuracionesPartida, IUnirsePartidaFachada unirsePartida) {
        this.configuracionesPartida = configuracionesPartida;
        this.unirsePartidaFachada = unirsePartida;
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
        unirsePartidaFachada.setJugadorSolicitante(jugadorsolicitante);

        // Obtener la solicitud actual si existe
        solicitud = obtenerSolicitud();

        // Si no existe solicitud, crear una nueva
        if (solicitud == null) {
            solicitud = unirsePartidaFachada.crearSolicitud(jugadorsolicitante);
        }

        // Enviar la solicitud al host
        unirsePartidaFachada.enviarSolicitudSalaEspera(solicitud);
    }

    /**
     * Obtiene la solicitud actual creada.
     *
     * @return SolicitudUnirse actual o null si no existe
     */
    @Override
    public SolicitudUnirse obtenerSolicitud() {
        solicitud = unirsePartidaFachada.getSolicitudActual();

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
        unirsePartidaFachada.cambiarEstadoSolicitud(solicitud, estadoSolicitud);

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
            // Usar la fachada directamente que implementa IUnirsePartidaEnvio
            unirsePartidaFachada.enviarVotoSolicitud(solicitud);

            String estado = solicitud.isSolicitudEstado() ? "ACEPTADA" : "RECHAZADA";
            System.out.println("✓ Respuesta " + estado + " enviada al solicitante");
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
    public void actualizarSolicitudUnirse(SolicitudUnirse solicitud) {
        // Actualizar la solicitud en el modelo
        this.solicitud = solicitud;

        //envia eventos que ahora escucha el jugadr que se unioo
        if (solicitud.isSolicitudEstado()) {
            unirsePartidaFachada.suscribirseASalaEspera();
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
        unirsePartidaFachada.enviarSolicitudSalaEspera(solicitud);
    }

    //revisar METODO PARA PROBAR CUANDO UNA PARTIDA ESTA EN CURSO Y RECHAZAR SOLICITUDES 
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

        if (unirsePartidaFachada != null) {

            try {
                unirsePartidaFachada.setPartida(partida);

            } catch (Exception e) {
                System.err.println("ERROR al conectar partida a UnirsePartida: " + e.getMessage());
            }

        } else {
            System.err.println("UnirsePartida es null. No se pudo establecer la conexión.");
        }
    }

    @Override
    public void buscarJugadorEnSalaEspera(JugadorSolicitanteDTO jugadorSolicitante) {
        unirsePartidaFachada.SolicitarJugadorEnSala(jugadorSolicitante);
    }

}
