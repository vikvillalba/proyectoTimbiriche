package ConfiguracionesFachada;

import ConfiguracionesFachada.Observer.ObservableEventos;
import ConfiguracionesFachada.Observer.ObservadorEventos;
import Entidades.Jugador;
import Entidades.TipoEvento;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.ConfiguracionesDTO;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PaqueteDTO;
import org.itson.dto.PartidaDTO;
import org.itson.dto.TableroDTO;

/**
 * A
 *
 * @author victoria
 */
public class ConfiguracionesPartida implements ConfiguracionesFachada, ObservableEventos {

    private IEmisor emisor;
    private ObservadorEventos<PartidaDTO> observadorConfiguraciones;
    private String host;
    private int puertoOrigen;
    private int puertoDestino;
    private PartidaDTO partida;


    public void configuracionesRecibidas(PaqueteDTO paquete) {
        PartidaDTO partida = PaqueteDTOAPartida(paquete);
        this.partida = partida;
        observadorConfiguraciones.actualizar(partida);

    }

    @Override
    public void solicitarConfiguraciones() {
        PaqueteDTO solicitud = new PaqueteDTO("solicitud de configuraciones", TipoEvento.OBTENER_CONFIGURACIONES_PARTIDA.toString());
        solicitud.setHost(host);
        solicitud.setPuertoOrigen(puertoOrigen);
        solicitud.setPuertoDestino(puertoDestino);

        emisor.enviarCambio(solicitud);
    }

    @Override
    public void iniciarConexion(List<Jugador> jugadores, TableroDTO tablero, Jugador sesion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void solicitarInicioJuego(Jugador jugador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void confirmarIncioJuego(Jugador jugador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void notificarEventoRecibido(PartidaDTO evento) {
        this.observadorConfiguraciones.actualizar(evento);
    }

    private PartidaDTO PaqueteDTOAPartida(PaqueteDTO<?> paquete) {
        if (paquete == null || paquete.getContenido() == null) {
            throw new IllegalArgumentException("Paquete null o sin contenido.");
        }

        Object contenido = paquete.getContenido();

        if (contenido instanceof PartidaDTO dto) {
            return dto;
        }

        if (contenido instanceof ConfiguracionesDTO config) {
            return new PartidaDTO(config.getTablero(), config.getJugadores());
        }

        if (contenido instanceof Map<?, ?> mapa) {
            Map<?, ?> tableroMap = (Map<?, ?>) mapa.get("tablero");
            List<?> jugadoresList = (List<?>) mapa.get("jugadores");

            TableroDTO tablero = new TableroDTO(
                    ((Number) tableroMap.get("alto")).intValue(),
                    ((Number) tableroMap.get("ancho")).intValue()
            );

            List<JugadorDTO> jugadores = new ArrayList<>();
            for (Object jObj : jugadoresList) {
                Map<?, ?> j = (Map<?, ?>) jObj;
                JugadorDTO dto = new JugadorDTO();
                dto.setId((String) j.get("id"));
                dto.setTurno(j.get("turno") != null && (Boolean) j.get("turno"));
                dto.setScore(j.get("score") != null ? ((Number) j.get("score")).intValue() : 0);
                dto.setListo(j.get("listo") != null && (Boolean) j.get("listo"));
                dto.setAvatar((String) j.get("avatar"));
                dto.setColor((String) j.get("color"));
                jugadores.add(dto);
            }

            return new PartidaDTO(tablero, jugadores);
        }

        throw new IllegalArgumentException(
                "Contenido del paquete no es PartidaDTO ni ConfiguracionesDTO."
        );
    }

    @Override
    public PartidaDTO getConfiguraciones() {
        return partida;
    }

    public void setEmisor(IEmisor emisor) {
        this.emisor = emisor;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuertoOrigen() {
        return puertoOrigen;
    }

    public void setPuertoOrigen(int puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    public int getPuertoDestino() {
        return puertoDestino;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    @Override
    public void agregarObservador(ObservadorEventos ob) {
        this.observadorConfiguraciones = ob;
    }

    @Override
    public void notificarInicioJuego(PartidaDTO partida) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
