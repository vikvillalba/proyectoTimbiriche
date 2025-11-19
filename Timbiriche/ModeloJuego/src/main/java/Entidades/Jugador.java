package Entidades;

/**
 * Clase que representa a un jugador de manera abstracta.
 *
 * @author victoria
 */
public class Jugador {

    private String nombre;
    private AvatarEnum avatar; //enums para color y avatar (tenemos 9 avatars nomas)
    private ColorEnum color;
    private int score;
    private boolean turno;

    /**
     * Construye a un nuevo jugador.
     *
     * @param nombre nombre del jugador
     * @param avatar enum correspondiente al avatar
     * @param color enum correspondiente al color
     * @param score puntos de juego inicializado en 0
     * @param turno inicializado en false
     */
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    /**
     * Suma un punto al score del jugador.
     */
    public void sumarScore() {
        this.score++;
    }
}
