package objetosPresentables;

import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author victoria
 */
public class JugadorPresentable {
    private String nombre;
    private Image avatar;
    private Color color;
    private int score;
    private boolean turno;

    public JugadorPresentable(String nombre, Image avatar, Color color, int score, boolean turno) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.score = score;
        this.turno = turno;
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

    public int getScore() {
        return score;
    }

    public boolean isTurno() {
        return turno;
    }
    
    

}
