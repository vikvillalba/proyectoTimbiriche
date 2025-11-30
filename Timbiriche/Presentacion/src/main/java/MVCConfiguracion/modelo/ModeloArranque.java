package MVCConfiguracion.modelo;

import ConfiguracionesFachada.ConfiguracionesFachada;
import DTO.JugadorSolicitanteDTO;
import Entidades.TipoEvento;
import MVCConfiguracion.observer.INotificadorUnirsePartida;
import MVCConfiguracion.observer.IPublicadorUnirsePartida;
import MVCConfiguracion.observer.ObservableConfiguraciones;
import MVCConfiguracion.observer.ObservadorConfiguraciones;
import MVCConfiguracion.observer.ObservadorEventoInicio;
import ModeloUnirsePartida.UnirsePartida;
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
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PaqueteDTO;

/**
 * Modelo de arranque que maneja la configuración de la partida y el proceso de unirse a partidas existentes.
 *
 * @author victoria
 */
public class ModeloArranque implements IModeloArranqueEscritura, IModeloArranqueLectura, ObservableConfiguraciones, IPublicadorUnirsePartida {

    private ObservadorConfiguraciones observadorConfiguraciones;
    private ConfiguracionesFachada configuracionesPartida;
    private static final Map<String, Color> COLORES = new HashMap<>();
    private static final Map<String, Image> AVATARES = new HashMap<>();

    //CU_UnirsePartida
    //modelo unirse partida LOGICA
    private UnirsePartida unirsePartida;

    //lista de los frms Notificados UNIRSE PARTIDA
    private List<INotificadorUnirsePartida> notificadosUnirsePartida = new ArrayList<>();

    //Solicitud de UnirsePartida
    private SolicitudUnirse solicitud;

    public ModeloArranque(ConfiguracionesFachada configuracionesPartida, UnirsePartida unirsePartida) {
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
        SolicitudUnirse solicitud = obtenerSolicitud();
        //si la solicitud es null crear una por primera vez
        if (solicitud == null) {
            //crear la solicitud en unirsePartida 
            unirsePartida.crearSolicitud(jugadorsolicitante);

        }

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
        //settear el etado de la solicitud que esta en unirsePartida

        unirsePartida.cambiarEstadoSolicitud(solicitud, estadoSolicitud);
        //obtine la solicitud seteada 
        solicitud = obtenerSolicitud();

    }

//
//    /**
//     * Establece el jugador host para las solicitudes de unirse.
//     *
//     * @param jugadorHost Jugador que es el host de la partida
//     */
//    public void setJugadorHost(JugadorConfig jugadorHost) {
//        if (jugadorHost == null) {
//            throw new IllegalArgumentException("El jugador host no puede ser nulo");
//        }
//
//        // Convertir JugadorConfig a JugadorConfigDTO
//        DTO.JugadorConfigDTO jugadorHostDTO = new DTO.JugadorConfigDTO(
//                jugadorHost.getNombre(),
//                obtenerNombreAvatar(jugadorHost.getAvatar()),
//                obtenerNombreColor(jugadorHost.getColor()),
//                jugadorHost.isListo(),
//                jugadorHost.isEsHost()
//        );
//
//        unirsePartida.setJugadorHost(jugadorHostDTO);
//        System.out.println("Jugador host establecido: " + jugadorHost.getNombre());
//    }
//
//    /**
//     * Obtiene el nombre del color desde el objeto Color.
//     *
//     * @param color Color a buscar
//     * @return Nombre del color o "desconocido"
//     */
//    private String obtenerNombreColor(Color color) {
//        for (Map.Entry<String, Color> entry : COLORES.entrySet()) {
//            if (entry.getValue().equals(color)) {
//                return entry.getKey();
//            }
//        }
//        return "desconocido";
//    }
//
//    /**
//     * Obtiene el nombre del avatar desde el objeto Image.
//     *
//     * @param avatar Imagen del avatar
//     * @return Nombre del avatar o "desconocido"
//     */
//    private String obtenerNombreAvatar(Image avatar) {
//        for (Map.Entry<String, Image> entry : AVATARES.entrySet()) {
//            if (entry.getValue().equals(avatar)) {
//                return entry.getKey();
//            }
//        }
//        return "desconocido";
//    }
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

}
