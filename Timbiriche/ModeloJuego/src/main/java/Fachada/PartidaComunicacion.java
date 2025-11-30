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
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author victoria
 */
public class PartidaComunicacion implements IReceptor{
    private Partida partida;
    private ConfiguracionesPartida configuraciones;

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public void setConfiguraciones(ConfiguracionesPartida configuraciones) {
        this.configuraciones = configuraciones;
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
                
                partida.inicioPartida();
                partida.obtenerJugadorTurno(paquete);
                break;
            }
//
            //case para elestado de partida si esta en cuerso y jugadores llenos 
//            case UNIRSE_PARTIDA:
//                partida.notificarEventoRecibido("Un jugador se unio a la partida");
//                break;

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
