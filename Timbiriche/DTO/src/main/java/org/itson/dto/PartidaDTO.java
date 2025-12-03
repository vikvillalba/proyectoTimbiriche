package org.itson.dto;

import java.util.List;

/**
 *
 * @author victoria
 */
public class PartidaDTO {
    private TableroDTO tablero;
    private List<JugadorDTO> jugadores;

    public PartidaDTO(TableroDTO tablero, List<JugadorDTO> jugadores) {
        this.tablero = tablero;
        this.jugadores = jugadores;
    }

    public PartidaDTO() {
    }

    public TableroDTO getTablero() {
        return tablero;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void setTablero(TableroDTO tablero) {
        this.tablero = tablero;
    }

    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }
    
    
}
