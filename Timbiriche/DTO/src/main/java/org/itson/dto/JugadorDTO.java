package org.itson.dto;

/**
 *
 * @author erika
 */
public class JugadorDTO {
    private String id;
    private boolean turno;

    public JugadorDTO() {
    }

    public JugadorDTO(String id, boolean turno) {
        this.id = id;
        this.turno = turno;
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
}
