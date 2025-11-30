package org.itson.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author victoria
 */
public class ConfiguracionesDTO {

    private TableroDTO tablero;
    private final List<JugadorDTO> jugadores = new ArrayList<>();

    public TableroDTO getTablero() {
        return tablero;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void agregarJugador(Object contenido) {
        if (contenido instanceof JugadorDTO jugador) {
            jugadores.add(jugador);
        } else if (contenido instanceof PaqueteDTO<?> paquete) {
            Object inner = paquete.getContenido();
            if (inner instanceof JugadorDTO jugador) {
                jugadores.add(jugador);
            } else if (inner instanceof Map<?, ?> mapa) {

                JugadorDTO jugador = new JugadorDTO();
                jugador.setId((String) mapa.get("id"));
                jugador.setTurno(Boolean.TRUE.equals(mapa.get("turno")));
                jugador.setScore(mapa.get("score") != null ? ((Number) mapa.get("score")).intValue() : 0);
                jugador.setListo(Boolean.TRUE.equals(mapa.get("listo")));
                jugador.setAvatar((String) mapa.get("avatar"));
                jugador.setColor((String) mapa.get("color"));
                jugadores.add(jugador);
            }
        }
    }

    public void setTablero(Object contenido) {
        if (contenido instanceof TableroDTO tableroObj) {
            this.tablero = tableroObj;
            return;
        }

        if (contenido instanceof PaqueteDTO<?> paquete) {
            Object inner = paquete.getContenido();

            if (inner instanceof TableroDTO tableroObj) {
                this.tablero = tableroObj;
            } else if (inner instanceof Map<?, ?> mapa) {
                int alto = ((Number) mapa.get("alto")).intValue();
                int ancho = ((Number) mapa.get("ancho")).intValue();
                this.tablero = new TableroDTO(alto, ancho);
            }
        }
    }

}
