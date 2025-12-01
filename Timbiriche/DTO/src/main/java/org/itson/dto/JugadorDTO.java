package org.itson.dto;

/**
 *
 * @author erika
 */
public class JugadorDTO {

    private String id;
    private boolean turno;
    private int score;

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

    @Override
    public String toString() {
        return "JugadorDTO{" + "id=" + id + ", turno=" + turno + ", score=" + score + '}';
    }
}
