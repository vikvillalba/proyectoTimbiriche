package Entidades;

/**
 *
 * @author victoria
 */
public class Jugador {

    private String nombre;
    private AvatarEnum avatar; //enums para color y avatar (tenemos 9 avatars nomas)
    private ColorEnum color;
    private int score;
    private boolean turno;

    public Jugador(String nombre, AvatarEnum avatar, ColorEnum color, int score, boolean turno) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.score = 0;
        this.turno = false;
    }

    public Jugador() {
    }

    public String getNombre() {
        return nombre;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    public AvatarEnum getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarEnum avatar) {
        this.avatar = avatar;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }
    
    public void sumarScore() {
        this.score++;
    }
}
