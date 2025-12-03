package ConfiguracionesFachada;

import ConfiguracionesFachada.Observer.ObservableEventos;
import ConfiguracionesFachada.Observer.ObservadorEventos;
import ConfiguracionesFachada.Observer.ObservadorSolicitudInicio;
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
    private ObservadorEventos<PartidaDTO> observadorEventos; // vista
    private ObservadorSolicitudInicio observadorSolicitudInicio;
    private String host;
    private int puertoOrigen;
    private int puertoDestino;
    private PartidaDTO partida;

    private boolean juegoIniciado = false;

    private List<JugadorDTO> jugadoresTurnos;

    public void turnosRepartidos(PaqueteDTO paquete) {
        this.jugadoresTurnos = convertirAListaJugadoresDTO(paquete);

        this.partida.setJugadores(jugadoresTurnos);
       
        iniciarPartida();
    }

    public void partidaIniciada(PaqueteDTO paquete) {
        PartidaDTO partidaDTO = PaqueteDTOAPartida(paquete);
        this.partida = partidaDTO;
        this.partida.setJugadores(jugadoresTurnos);
   
        notificarInicioJuego();

    }

    public void configuracionesRecibidas(PaqueteDTO paquete) {
        PartidaDTO partidaDTO = PaqueteDTOAPartida(paquete);
        this.partida = partidaDTO;
        observadorEventos.actualizar(partida);
        if (validarEstadoJugadores() && !juegoIniciado) {
            System.out.println("inicia el juego");
            solicitarTurnos();

        }

    }

    public void iniciarPartida() {
        if (juegoIniciado) {
            return;
        }
        if (!validarEstadoJugadores()) {
            return;
        }
        juegoIniciado = true;

        PaqueteDTO solicitud = new PaqueteDTO(partida, TipoEvento.INICIO_PARTIDA.toString());
        solicitud.setHost(host);
        solicitud.setPuertoOrigen(puertoOrigen);
        solicitud.setPuertoDestino(puertoDestino);
        emisor.enviarCambio(solicitud);
    }

    public void solicitarTurnos() {
        PaqueteDTO turnos = new PaqueteDTO(partida.getJugadores(), "SOLICITAR_TURNOS");
        turnos.setHost(host);
        turnos.setPuertoOrigen(puertoOrigen);
        turnos.setPuertoDestino(puertoDestino);
        emisor.enviarCambio(turnos);

    }

    @Override
    public void notificarInicioJuego() {
        observadorEventos.iniciarJuego(partida);
    }

    private List<JugadorDTO> convertirAListaJugadoresDTO(Object contenido) {
        List<JugadorDTO> resultado = new ArrayList<>();

        if (contenido instanceof List) {
            List<?> lista = (List<?>) contenido;

            for (Object item : lista) {
                JugadorDTO dto = null;

                if (item instanceof JugadorDTO) {
                    dto = (JugadorDTO) item;

                } else if (item instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) item;

                    String id = (String) map.get("id");
                    boolean turno = map.get("turno") != null && (Boolean) map.get("turno");

                    dto = new JugadorDTO(id, turno);

                    if (map.containsKey("score")) {
                        dto.setScore(((Number) map.get("score")).intValue());
                    }

                    if (map.containsKey("listo")) {
                        dto.setListo(map.get("listo") != null && (Boolean) map.get("listo"));
                    }

                    if (map.containsKey("avatar")) {
                        dto.setAvatar((String) map.get("avatar"));
                    }

                    if (map.containsKey("color")) {
                        dto.setColor((String) map.get("color"));
                    }
                }

                resultado.add(dto);
            }
        }

        return resultado;
    }

    @Override
    public void solicitarConfiguraciones() {
        PaqueteDTO solicitud = new PaqueteDTO("solicitud de configuraciones", TipoEvento.OBTENER_CONFIGURACIONES_PARTIDA.toString());
        solicitud.setHost(host);
        solicitud.setPuertoOrigen(puertoOrigen);
        solicitud.setPuertoDestino(puertoDestino);

        emisor.enviarCambio(solicitud);
    }

    private boolean validarEstadoJugadores() {
        List<JugadorDTO> jugadores = partida.getJugadores();
        int numeroJugadores = jugadores.size();
        int confirmados = 0;

        for (JugadorDTO jugadorDTO : jugadores) {
            if (jugadorDTO.isListo()) {
                confirmados++;
            }
        }

        if (confirmados == numeroJugadores) {
            return true;
        }
        return false;
    }

    @Override
    public void solicitarInicioJuego(JugadorDTO jugador) {

        if (validarEstadoJugadores() && !juegoIniciado) {
            System.out.println("JUEGO INICIAO");
            solicitarTurnos();

            return;
        }

        PaqueteDTO solicitud = new PaqueteDTO(jugador, TipoEvento.SOLICITAR_INICIAR_PARTIDA.toString());
        solicitud.setHost(host);
        solicitud.setPuertoOrigen(puertoOrigen);
        solicitud.setPuertoDestino(puertoDestino);

        emisor.enviarCambio(solicitud);
    }

    public void recibirSolicitudInicioJuego(PaqueteDTO paquete) {
        PartidaDTO partida = PaqueteDTOAPartida(paquete);
        this.partida = partida;

        notificarSolictudInicio(partida);

    }

    @Override
    public void confirmarIncioJuego(JugadorDTO jugador) {
        jugador.setListo(true);
        PaqueteDTO solicitud = new PaqueteDTO(jugador, TipoEvento.CONFIRMAR_INICIO_PARTIDA.toString());
        solicitud.setHost(host);
        solicitud.setPuertoOrigen(puertoOrigen);
        solicitud.setPuertoDestino(puertoDestino);

        emisor.enviarCambio(solicitud);
    }

    @Override
    public void notificarEventoRecibido(PartidaDTO evento) {
        this.observadorEventos.actualizar(evento);
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

        if (contenido instanceof Map mapa) {
            PartidaDTO partidaDTO = new PartidaDTO();

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
                this.jugadoresTurnos = jugadores;
            } else {
                partidaDTO.setJugadores(jugadoresTurnos);
            }

            return partidaDTO;
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
        this.observadorEventos = ob;
    }

    @Override
    public void agregarObservadorSolicitudInicio(ObservadorSolicitudInicio ob) {
        observadorSolicitudInicio = ob;
    }

    @Override
    public void notificarSolictudInicio(PartidaDTO partida) {
        observadorSolicitudInicio.actualizar(partida);
    }

}
