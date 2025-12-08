package Fachada;

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
import com.google.gson.Gson;
import java.util.Map;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.ConfiguracionesDTO;
import org.itson.dto.PaqueteDTO;
import org.itson.observadores.ObservadorConfiguracionLocal;

/**
 *
 * @author victoria
 */
public class PartidaComunicacion implements IReceptor, ObservadorConfiguracionLocal {

    private Partida partida;

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        System.out.println("[Partida] evento recibido: " + paquete.getTipoEvento());
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

            case SOLICITAR_INICIAR_PARTIDA:

            case INICIO_PARTIDA: {
                partida.obtenerJugadorTurno(paquete);
                partida.inicioPartida();
                
                break;
            }

            case UNIRSE_PARTIDA:
                partida.notificarEventoRecibido("Un jugador se unio a la partida");
                break;

            case ABANDONAR_PARTIDA:
                partida.notificarEventoRecibido("Un jugador abandono la partida");
                break;

            case CONFIGURAR_PARTIDA:
                partida.notificarEventoRecibido("Partida configurada");

                if (paquete.getContenido() instanceof ConfiguracionesDTO) {
                    ConfiguracionesDTO configuracion = (ConfiguracionesDTO) paquete.getContenido();
                    partida.recibirConfiguracionInicial(configuracion);
                } else if (paquete.getContenido() instanceof Map) {

                    Map<?, ?> mapContenido = (Map<?, ?>) paquete.getContenido();

                    Gson gson = new Gson();
                    String jsonConfig = gson.toJson(mapContenido);

                    recibirConfiguracionDesdeJSON(jsonConfig);

                } else {

                    partida.notificarEventoRecibido("ERROR: Contenido de CONFIGURAR_PARTIDA no es un DTO/Map esperado.");
                }
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

    @Override
    public void configuracionRecibida(ConfiguracionesDTO configuracion) {
        System.out.println("[PartidaComunicacion - Local] Aplicando configuración de partida (Host).");
        partida.recibirConfiguracionInicial(configuracion);
    }

    public void recibirConfiguracionDesdeJSON(String jsonConfig) {
        Gson gson = new Gson();
        try {
            ConfiguracionesDTO configuracion = gson.fromJson(jsonConfig, ConfiguracionesDTO.class);
            partida.recibirConfiguracionInicial(configuracion);
        } catch (Exception e) {
            System.err.println("[Partida] ERROR al deserializar ConfiguracionesDTO: " + e.getMessage());
            partida.notificarEventoRecibido("ERROR de configuración: " + e.getMessage());
        }
    }
}
