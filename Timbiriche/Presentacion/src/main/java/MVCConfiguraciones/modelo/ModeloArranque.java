package MVCConfiguraciones.modelo;

import Configuraciones.Observer.ObserverEvento;
import ConfiguracionesFachada.ConfiguracionesFachada;
import MVCConfiguraciones.observer.ObservableRegistro;
import MVCConfiguraciones.observer.ObserverRegistro;
import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import org.itson.dto.JugadorNuevoDTO;

/**
 *
 * @author victoria
 */
public class ModeloArranque implements IModeloArranqueEscritura, IModeloArranqueLectura, ObservableRegistro, ObserverEvento {

    private static final Map<String, Color> COLORES = new HashMap<>();
    private static final Map<String, Image> AVATARES = new HashMap<>();
    private ObserverRegistro observerRegistro;
    private ConfiguracionesFachada configuracion;

    public ModeloArranque(ConfiguracionesFachada configuracion) {
        this.configuracion = configuracion;
    }

    static {
        COLORES.put("rojo_pastel", new Color(220, 20, 60));
        COLORES.put("azul_pastel", new Color(135, 206, 235));
        COLORES.put("verde_pastel", new Color(143, 188, 139));
        COLORES.put("amarillo_pastel", new Color(242, 201, 104));
        COLORES.put("magenta", new Color(201, 91, 170));
        COLORES.put("naranja_pastel", new Color(240, 120, 77));
        COLORES.put("rosa_pastel", new Color(247, 163, 176));
        COLORES.put("azul_marino", new Color(49, 49, 125));
        COLORES.put("moras", new Color(147, 112, 219));

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
    public Map<String, Color> getColores() {
        return new HashMap<>(COLORES);
    }

    @Override
    public Map<String, Image> getAvatares() {
        return new HashMap<>(AVATARES);
    }

    @Override
    public void registrarJugador(JugadorNuevoDTO jugador) {
        System.out.println("[Modelo] registrar jugador: " + jugador.getNombre());
        configuracion.registrarJugador(jugador);
    }

    @Override
    public void solicitarElementosUso() {
        System.out.println("[Modelo] solicitar asuntos");
        configuracion.solicitarElementosUso();
    }

    @Override
    public void validarJugador(List<String> usados) {
        observerRegistro.validarJugador(usados);
    }

    @Override
    public void agregarObserver(ObserverRegistro o) {
        this.observerRegistro = o;
    }

    @Override
    public void notificarObserver(List<String> usados) {
        observerRegistro.validarJugador(usados);
    }
}
