package org.itson.dto;

/**
 *
 * @author erika
 */
public class JugadorDTO {

    private String id;
    private boolean turno;
    private int score;
    private boolean listo;
    private String avatar;
    private String color;

    public JugadorDTO() {
    }

    public JugadorDTO(String id, boolean turno) {
        this.id = id;
        this.turno = turno;
    }

    public JugadorDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isListo() {
        return listo;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    
    
}
