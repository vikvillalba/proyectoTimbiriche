package Entidades;



/**
 *
 * @author victoria
 */
public class Jugador {
    private String nombre;
   // private Image avatar; enums para color y avatar (tenemos 8 avatars nomas)
   // private Color color;
    private int score;
    private boolean turno;

    public Jugador(String nombre) {
        this.nombre = nombre;
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
    
    
    
    
}
