package objetosPresentables;

import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author victoria
 */
public class JugadorConfig {
    private String nombre;
    private Image avatar;
    private Color color;
    private boolean listo;

    public JugadorConfig(String nombre, Image avatar, Color color, boolean listo) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.listo = listo;
    }

    public String getNombre() {
        return nombre;
    }

    public Image getAvatar() {
        return avatar;
    }

    public Color getColor() {
        return color;
    }

    public boolean isListo() {
        return listo;
    }
    
    
}
