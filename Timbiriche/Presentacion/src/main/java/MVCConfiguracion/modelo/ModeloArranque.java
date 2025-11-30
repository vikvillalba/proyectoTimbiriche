package MVCConfiguracion.modelo;

import ConfiguracionesFachada.ConfiguracionesFachada;
import ConfiguracionesFachada.Observer.ObservadorEventos;
import ConfiguracionesFachada.Observer.ObservadorSolicitudInicio;
import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import MVCConfiguracion.observer.ObservableConfiguraciones;
import MVCConfiguracion.observer.ObservadorConfiguraciones;
import MVCConfiguracion.observer.ObservadorEventoInicio;
import MVCConfiguracion.observer.ObservadorSolicitudes;
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
import objetosPresentables.TableroConfig;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PartidaDTO;
import org.itson.dto.TableroDTO;

/**
 *
 * @author victoria
 */
public class ModeloArranque implements IModeloArranqueEscritura, IModeloArranqueLectura, ObservableConfiguraciones, ObservadorEventos<Object>, ObservadorSolicitudInicio {

    private ObservadorConfiguraciones observadorConfiguraciones;
    private ObservadorEventoInicio observadorInicio;
    private ObservadorSolicitudes observadorSolicitudes;
    private ConfiguracionesFachada configuracionesPartida;
    private JugadorDTO sesion;

    private PartidaDTO configuraciones;

    private boolean vista = false;

    private static final Map<String, Color> COLORES = new HashMap<>();
    private static final Map<String, Image> AVATARES = new HashMap<>();

    public ModeloArranque(ConfiguracionesFachada configuracionesPartida) {
        this.configuracionesPartida = configuracionesPartida;
    }

    static {
        // Mapa con los colores que se usan
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

    private Image obtenerAvatar(String avatarEnum) {
        return AVATARES.get(avatarEnum.toLowerCase());
    }

    private Color obtenerColor(String colorEnum) {
        return COLORES.get(colorEnum.toLowerCase());
    }

    private ColorEnum getColorEnum(Color color) {
        for (Map.Entry<String, Color> entry : COLORES.entrySet()) {
            if (entry.getValue().equals(color)) {
                return ColorEnum.valueOf(entry.getKey().toUpperCase());
            }
        }
        return null;
    }

    private AvatarEnum getAvatarEnum(Image avatar) {
        for (Map.Entry<String, Image> entry : AVATARES.entrySet()) {
            if (entry.getValue() == avatar) {
                return AvatarEnum.valueOf(entry.getKey().toUpperCase());
            }
        }
        return null;
    }

    @Override
    public PartidaPresentable getConfiguracionesPartida() {
        List<JugadorDTO> jugadoresDTO = configuraciones.getJugadores();
        TableroDTO tableroDTO = configuraciones.getTablero();

        // pasar a objetos presentables
        List<JugadorConfig> jugadores = new ArrayList<>();

        for (JugadorDTO dto : jugadoresDTO) {
            JugadorConfig jugador = new JugadorConfig(dto.getId(),
                    obtenerAvatar(dto.getAvatar()),
                    obtenerColor(dto.getColor()),
                    dto.isListo()
            );
            jugadores.add(jugador);
        }

        TableroConfig tablero = new TableroConfig(tableroDTO.getAlto(), tableroDTO.getAncho());

        PartidaPresentable partida = new PartidaPresentable(jugadores, tablero);
        notificarConfiguraciones(partida);
        vista = true;
        return partida;

    }

    @Override
    public JugadorConfig getSesion() {
        JugadorConfig jugador = new JugadorConfig(sesion.getId(),
                obtenerAvatar(sesion.getAvatar()),
                obtenerColor(sesion.getColor()),
                sesion.isListo()
        );
        return jugador;
    }

    @Override
    public void iniciarConexion(List<JugadorConfig> jugadores, int altoTablero, int anchoTablero, JugadorConfig sesion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private JugadorDTO presentableADTO(JugadorConfig jugador) {
        JugadorDTO jugadorDTO = new JugadorDTO();
        jugadorDTO.setId(jugador.getNombre());
        jugadorDTO.setAvatar(getAvatarEnum(jugador.getAvatar()).toString());
        jugadorDTO.setColor(getColorEnum(jugador.getColor()).toString());
        jugadorDTO.setScore(0);
        jugadorDTO.setTurno(false);
        jugadorDTO.setListo(jugador.isListo());

        return jugadorDTO;
    }

    @Override
    public void solicitarInicioConexion(JugadorConfig jugador) {
        // llamada al modelo de juego
        configuracionesPartida.solicitarInicioJuego(presentableADTO(jugador));
    }

    @Override
    public void confirmarInicioJuego(JugadorConfig jugador) {

    }

    @Override
    public void agregarObservadorConfiguraciones(ObservadorConfiguraciones ob) {
        this.observadorConfiguraciones = ob;
    }

    @Override
    public void agregarObservadorEventoInicio(ObservadorEventoInicio ob) {
        this.observadorInicio = ob;
    }

    @Override
    public void notificarInicioPartida(PartidaPresentable partida) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void notificarConfiguraciones(PartidaPresentable partida) {
        observadorConfiguraciones.actualizar(partida);
    }

    @Override
    public boolean isVista() {
        return vista;
    }

    @Override
    public void actualizar(Object cambio) {
        if (cambio instanceof PartidaDTO) {
            this.configuraciones = (PartidaDTO) cambio;
        }

        notificarConfiguraciones(getConfiguracionesPartida());
    }

    @Override
    public void solicitarConfiguraciones() {
        configuracionesPartida.solicitarConfiguraciones();
    }

    public void setSesion(JugadorDTO sesion) {
        this.sesion = sesion;
    }

    @Override
    public void agregarObservadorSolicitudes(ObservadorSolicitudes ob) {
        this.observadorSolicitudes = ob;
    }

    @Override
    public void notificarObservadorSolicitudes() {
        observadorSolicitudes.mostrar();
    }

    @Override
    public void actualizar(PartidaDTO configuracionesPartida) {
        this.configuraciones = configuracionesPartida;
        notificarConfiguraciones(getConfiguracionesPartida());
        notificarObservadorSolicitudes();
    }

}
