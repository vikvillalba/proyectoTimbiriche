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
    public boolean esHost;

    public JugadorConfig() {
    }

    
    public JugadorConfig(String nombre, Image avatar, Color color, boolean listo) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.listo = listo;
    }

    //CU_UnirsePartida
    public JugadorConfig(String nombre, Image avatar, Color color, boolean listo, boolean esHost) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.listo = listo;
        this.esHost = esHost;
    }

    public boolean isEsHost() {
        return esHost;
    }

    public void setEsHost(boolean esHost) {
        this.esHost = esHost;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }
    
    

}
