package org.itson.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        JugadorDTO jugadorAGregar = null;

        if (contenido instanceof JugadorDTO jugador) {
            jugadorAGregar = jugador;
        } else if (contenido instanceof PaqueteDTO<?> paquete) {
            Object inner = paquete.getContenido();
            if (inner instanceof JugadorDTO jugador) {
                jugadorAGregar = jugador;
            } else if (inner instanceof Map<?, ?> mapa) {
                String id = (String) mapa.get("id");

                Optional<JugadorDTO> existente = jugadores.stream()
                        .filter(j -> j.getId().equals(id))
                        .findFirst();

                if (existente.isPresent()) {
                    JugadorDTO j = existente.get();
                    j.setTurno(Boolean.TRUE.equals(mapa.get("turno")));
                    j.setScore(mapa.get("score") != null ? ((Number) mapa.get("score")).intValue() : 0);
                    j.setListo(Boolean.TRUE.equals(mapa.get("listo")));
                    j.setAvatar((String) mapa.get("avatar"));
                    j.setColor((String) mapa.get("color"));
                    return;
                }

                jugadorAGregar = new JugadorDTO();
                jugadorAGregar.setId(id);
                jugadorAGregar.setTurno(Boolean.TRUE.equals(mapa.get("turno")));
                jugadorAGregar.setScore(mapa.get("score") != null ? ((Number) mapa.get("score")).intValue() : 0);
                jugadorAGregar.setListo(Boolean.TRUE.equals(mapa.get("listo")));
                jugadorAGregar.setAvatar((String) mapa.get("avatar"));
                jugadorAGregar.setColor((String) mapa.get("color"));
            }
        }

        if (jugadorAGregar != null) {
            jugadores.add(jugadorAGregar);
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

    public void setJugadores(Object contenido) {
        jugadores.clear();

        List<JugadorDTO> nuevos = convertirAListaJugadoresDTO(contenido);

        if (nuevos != null) {
            jugadores.addAll(nuevos);
        }
    }

    private List<JugadorDTO> convertirAListaJugadoresDTO(Object contenido) {
        List<JugadorDTO> resultado = new ArrayList<>();

        if (contenido instanceof List<?> lista) {
            for (Object item : lista) {
                JugadorDTO dto = mapToJugadorDTO(item);
                if (dto != null) {
                    resultado.add(dto);
                }
            }

        } else if (contenido instanceof PaqueteDTO<?> paquete) {
            return convertirAListaJugadoresDTO(paquete.getContenido());

        } else {
            // Caso único: un jugador solo
            JugadorDTO dto = mapToJugadorDTO(contenido);
            if (dto != null) {
                resultado.add(dto);
            }
        }

        return resultado;
    }

    private JugadorDTO mapToJugadorDTO(Object item) {
        if (item instanceof JugadorDTO jugador) {
            return jugador;
        }

        if (item instanceof Map<?, ?> mapa) {
            JugadorDTO dto = new JugadorDTO();

            dto.setId((String) mapa.get("id"));
            dto.setTurno(Boolean.TRUE.equals(mapa.get("turno")));
            dto.setScore(mapa.get("score") != null ? ((Number) mapa.get("score")).intValue() : 0);
            dto.setListo(Boolean.TRUE.equals(mapa.get("listo")));
            dto.setAvatar((String) mapa.get("avatar"));
            dto.setColor((String) mapa.get("color"));

            return dto;
        }

        return null;
    }

}
