package Fachada;

import ConfiguracionesFachada.ConfiguracionesPartida;
import Entidades.TipoEvento;
import static Entidades.TipoEvento.ABANDONAR_PARTIDA;
import static Entidades.TipoEvento.ACTUALIZAR_PUNTOS;
import static Entidades.TipoEvento.CONFIGURAR_PARTIDA;
import static Entidades.TipoEvento.INICIO_PARTIDA;
import static Entidades.TipoEvento.NUEVA_LINEA;
import static Entidades.TipoEvento.SOLICITAR_FINALIZAR_PARTIDA;
import static Entidades.TipoEvento.SOLICITAR_INICIAR_PARTIDA;
import static Entidades.TipoEvento.TURNO_ACTUALIZADO;
import static Entidades.TipoEvento.UNIRSE_PARTIDA;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PaqueteDTO;
import org.itson.dto.PartidaDTO;
import org.itson.dto.TableroDTO;

/**
 *
 * @author victoria
 */
public class PartidaComunicacion implements IReceptor {

    private Partida partida;
    private ConfiguracionesPartida configuraciones;
    private boolean inicioRecibido = false;
    private boolean turnosRecibidos = false;
    private List<JugadorDTO> jugadores;

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public void setConfiguraciones(ConfiguracionesPartida configuraciones) {
        this.configuraciones = configuraciones;
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        System.out.println("[ModeloJuego] evento recibido: " + paquete.getTipoEvento());
        TipoEvento tipo;

        try {
            tipo = TipoEvento.valueOf(paquete.getTipoEvento());
        } catch (IllegalArgumentException e) {
            partida.notificarEventoRecibido("ERROR: Tipo de evento desconocido: " + paquete.getTipoEvento());
            return;
        }

        switch (tipo) {
            case NUEVA_LINEA: {
                partida.nuevaLinea(paquete);
                break;
            }

            case TURNO_ACTUALIZADO: {
                partida.turnoActualizado(paquete);
                break;
            }

            case OBTENER_CONFIGURACIONES_PARTIDA: {
                configuraciones.configuracionesRecibidas(paquete);
                break;
            }
            case SOLICITAR_INICIAR_PARTIDA: {
                configuraciones.recibirSolicitudInicioJuego(paquete);
                break;
            }

            case CONFIRMAR_INICIO_PARTIDA: {
                configuraciones.configuracionesRecibidas(paquete);
                break;
            }
            case INICIO_PARTIDA:
                if (!inicioRecibido && paquete.getContenido() != null) {
                    PartidaDTO partidaDTO = null;
                    Object contenido = paquete.getContenido();

                    if (contenido instanceof PartidaDTO pdto) {
                        partidaDTO = pdto;
                    } else if (contenido instanceof Map mapa) {
                        partidaDTO = new PartidaDTO();

                        Map<String, Object> tableroMap = (Map<String, Object>) mapa.get("tablero");
                        if (tableroMap != null) {
                            TableroDTO tablero = new TableroDTO();
                            tablero.setAlto(((Number) tableroMap.get("alto")).intValue());
                            tablero.setAncho(((Number) tableroMap.get("ancho")).intValue());
                            partidaDTO.setTablero(tablero);
                        }

                        List<Map<String, Object>> jugadoresMap = (List<Map<String, Object>>) mapa.get("jugadores");
                        List<JugadorDTO> jugadores = new ArrayList<>();


                        if (jugadoresMap != null && !jugadoresMap.isEmpty()) {
                            for (Map<String, Object> jMap : jugadoresMap) {
                                JugadorDTO jugador = new JugadorDTO();
                                jugador.setId((String) jMap.get("id"));
                                jugador.setTurno(jMap.get("turno") != null && (Boolean) jMap.get("turno"));
                                jugador.setScore(jMap.get("score") != null ? ((Number) jMap.get("score")).intValue() : 0);
                                jugador.setListo(jMap.get("listo") != null && (Boolean) jMap.get("listo"));
                                jugador.setAvatar((String) jMap.get("avatar"));
                                jugador.setColor((String) jMap.get("color"));
                                jugadores.add(jugador);
                              
                            }
                            partidaDTO.setJugadores(jugadores);
                            this.jugadores = jugadores;
                            

                        } else {
                            partidaDTO.setJugadores(new ArrayList<>());
                        }
                    }

                    if (partidaDTO != null) {
                        configuraciones.partidaIniciada(paquete);
                        inicioRecibido = true;
                    }
                }
                break;

            case TURNOS_REPARTIDOS:
//                if (!turnosRecibidos) {
                    configuraciones.turnosRepartidos(paquete);
//                    turnosRecibidos = true;   
//                } 
                break;
                
            case UNIRSE_PARTIDA:
                partida.notificarEventoRecibido("Un jugador se unio a la partida");
                break;

            case ABANDONAR_PARTIDA:
                partida.notificarEventoRecibido("Un jugador abandono la partida");
                break;

            case CONFIGURAR_PARTIDA:
                partida.notificarEventoRecibido("Partida configurada");
                break;

            case SOLICITAR_FINALIZAR_PARTIDA:
                partida.notificarEventoRecibido("Se solicito finalizar la partida");
                break;

            case ACTUALIZAR_PUNTOS:
                partida.actualizarPuntos(paquete);
                break;

            default:
                partida.notificarEventoRecibido("Evento no manejado: " + tipo);
        }
    }
}
