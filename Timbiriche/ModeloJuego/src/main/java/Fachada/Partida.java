package Fachada;

import Entidades.Cuadro;
import Entidades.Jugador;
import Entidades.Linea;
import Entidades.Punto;
import Entidades.Tablero;
import Entidades.TipoEvento;
import static Entidades.TipoEvento.*;
import Mapper.MapperJugadores;
import Observer.ObservableEventos;
import Observer.ObservadorEventos;
import Observer.ObservadorInicio;
import Observer.ObservadorJugadores;
import excepciones.PartidaExcepcion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PaqueteDTO;
import org.itson.dto.PuntoDTO;

/**
 * Clase que representa una partida de juego. Engloba a todas las clases
 * necesarias para iniciar una partida.
 *
 * @author victoria
 */
public class Partida implements PartidaFachada, IReceptor, ObservableEventos {

    private Tablero tablero;

    private ObservadorInicio observadorInicioJuego;
    private List<Jugador> jugadores;
    private Jugador jugadorEnTurno;
    private Jugador jugadorSesion;
    private IEmisor emisor;
    private MapperJugadores mapperJugadores;
    private List<ObservadorJugadores> observadoresJugadores = new ArrayList<>();
    private List<ObservadorEventos<?>> observadoresEventos = new ArrayList<>();

    private String host;
    private int puertoOrigen;
    private int puertoDestino;

    /**
     * Constructor de partida.
     *
     * @param jugadores jugadores que estarán en la partida
     * @param alto alto del tablero
     * @param ancho ancho del tablero
     *
     * inicializa un tablero
     *
     */
    public Partida(List<Jugador> jugadores, int alto, int ancho) {
        // tablero mock
        this.jugadores = jugadores;
        this.tablero = new Tablero(alto, ancho);
        this.mapperJugadores = new MapperJugadores();
    }

    @Override
    public Punto[] seleccionarPuntos(Punto origen, Punto destino, Jugador jugadorActual) {
        return new Punto[]{origen, destino};
    }

    @Override
    public boolean validarPuntos(Punto origen, Punto destino) throws PartidaExcepcion {
        for (Linea linea : tablero.getLineasExistentes()) {
            // validar que la linea no exista 
            if ((linea.getOrigen().equals(origen) && linea.getDestino().equals(destino)) || (linea.getOrigen().equals(destino) && linea.getDestino().equals(origen))) {
                throw new PartidaExcepcion("Los puntos seleccionados forman una línea que ya existe.");
            }
        }
        // validar que el punto origen y destino si sean adyacentes (q no sean diagonales o no tengan nada q ver)
        if (!(Objects.equals(origen.getArriba(), destino)
                || Objects.equals(origen.getAbajo(), destino)
                || Objects.equals(origen.getIzquierda(), destino)
                || Objects.equals(origen.getDerecha(), destino))) {
            throw new PartidaExcepcion("Los puntos seleccionados no se pueden conectar.");
        }
        // si se puede realizar la jugada: checar turnero
        // hacer paquete dto con la jugada 
        PuntoDTO[] puntos = new PuntoDTO[]{new PuntoDTO(origen.getX(), origen.getY()), new PuntoDTO(destino.getX(), destino.getY())};
        PaqueteDTO paquete = new PaqueteDTO(puntos, TipoEvento.NUEVA_LINEA.toString());
        paquete.setHost(this.host);
        paquete.setPuertoOrigen(this.puertoOrigen);
        paquete.setPuertoDestino(puertoDestino);
        emisor.enviarCambio(paquete);
        boolean cuadroCompletado = tablero.unirPuntos(origen, destino, jugadorEnTurno);
        if (cuadroCompletado) {
            notificarObservadorJugadores();
            List<JugadorDTO> jugadoresDTO = new ArrayList<>();
            for (Jugador j : jugadores) {
                JugadorDTO dto = new JugadorDTO(j.getNombre(), j.isTurno());
                dto.setScore(j.getScore());
                jugadoresDTO.add(dto);
            }
            PaqueteDTO puntajeActualizado = new PaqueteDTO(jugadoresDTO, TipoEvento.ACTUALIZAR_PUNTOS.toString());
            puntajeActualizado.setHost(this.host);
            puntajeActualizado.setPuertoOrigen(this.puertoOrigen);
            puntajeActualizado.setPuertoDestino(puertoDestino);
            emisor.enviarCambio(puntajeActualizado);
        }
        return cuadroCompletado;
    }

    @Override
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public Punto getPuntoTablero(int x, int y) {
        return tablero.getPunto(x, y);
    }

    @Override
    public Punto[][] getTablero() {
        return tablero.getPuntos();
    }

    @Override
    public List<Linea> getLineasTablero() {
        return tablero.getLineasExistentes();
    }

    @Override
    public void notificarObservadorInicioJuego() {
        if (observadorInicioJuego != null) {
            observadorInicioJuego.iniciarJuego();
        }
    }

    @Override
    public void agregarObservadorInicioJuego(ObservadorInicio ob) {
        observadorInicioJuego = ob;
    }

    @Override
    public List<Cuadro> getCuadrosTablero() {
        return tablero.getCuadrosExistentes();
    }

    @Override
    public void agregarObservadorJugadores(ObservadorJugadores ob) {
        this.observadoresJugadores.add(ob);
    }

    @Override
    public void notificarObservadorJugadores() {
        for (ObservadorJugadores ob : observadoresJugadores) {
            ob.actualizar(jugadores);
        }
    }

    @Override
    public void actualizarTurno() {
        JugadorDTO jugadorEnTurnoDTO = mapperJugadores.toDTO(jugadorEnTurno);
        PaqueteDTO paquete = new PaqueteDTO(jugadorEnTurnoDTO, TipoEvento.ACTUALIZAR_TURNO.toString());
        paquete.setHost(this.host);
        paquete.setPuertoOrigen(this.puertoOrigen);
        paquete.setPuertoDestino(puertoDestino);
        emisor.enviarCambio(paquete);
    }

    @Override
    public void NuevaLinea(PaqueteDTO paquete) {
        // Convertir contenido a PuntoDTO[]
        PuntoDTO[] puntosDTO = convertirAPuntosDTO(paquete.getContenido());
        if (puntosDTO == null || puntosDTO.length != 2) {
            notificarEventoRecibido("ERROR: PuntoDTO[] invalido en NUEVA_LINEA");
            return;
        }
        // Convertir PuntoDTO a Punto del tablero local
        Punto origen = tablero.getPunto(puntosDTO[0].getX(), puntosDTO[0].getY());
        Punto destino = tablero.getPunto(puntosDTO[1].getX(), puntosDTO[1].getY());
        if (origen == null || destino == null) {
            notificarEventoRecibido("ERROR: Puntos no encontrados en tablero local");
            return;
        }
        // Verificar si la linea ya existe (evita duplicados)
        for (Linea linea : tablero.getLineasExistentes()) {
            if ((linea.getOrigen().equals(origen) && linea.getDestino().equals(destino))
                    || (linea.getOrigen().equals(destino) && linea.getDestino().equals(origen))) {
                System.out.println("[Partida] Linea ya existe, ignorando evento duplicado");
                return;
            }
        }
        // Actualizar tablero local
        boolean hizoCuadro = tablero.unirPuntos(origen, destino, jugadorEnTurno);
        notificarEventoRecibido("Linea agregada: (" + origen.getX() + "," + origen.getY()
                + ") - (" + destino.getX() + "," + destino.getY() + ")");
        // Notificar a los observadores para actualizar la vista
        for (ObservadorEventos ob : observadoresEventos) {
            ob.actualizar(tablero);
        }
    }

    @Override
    public void TurnoActualizado(PaqueteDTO paquete) {
        // Convertir contenido a JugadorDTO
        JugadorDTO jugadorTurnoDTO = convertirAJugadorDTO(paquete.getContenido());

        System.out.println("[Partida] Jugador en turno segun DTO: " + jugadorTurnoDTO.getId());

        boolean turnoCambio = false;
        for (Jugador j : jugadores) {
            if (j.getNombre().equals(jugadorTurnoDTO.getId())) {
                if (!j.isTurno()) {
                    j.setTurno(true);
                    this.jugadorEnTurno = j;
                    turnoCambio = true;
                    System.out.println("[Partida] Turno asignado a: " + j.getNombre());
                }
            } else {
                j.setTurno(false);
            }
        }

        if (turnoCambio) {
            for (ObservadorJugadores ob : observadoresJugadores) {
                ob.actualizar(jugadores);
            }
            notificarEventoRecibido("Turno actualizado: " + jugadorTurnoDTO.getId());
        }
    }

    @Override
    public void InicioPartida() {

        notificarObservadorInicioJuego();
        notificarObservadorJugadores();
        notificarEventoRecibido("Partida iniciada");
    }

    private void obtenerJugadorTurno(PaqueteDTO paquete) {
        // Convertir contenido a List<JugadorDTO>
        List<JugadorDTO> jugadoresDTO = convertirAListaJugadoresDTO(paquete.getContenido());

        for (JugadorDTO dto : jugadoresDTO) {
            for (Jugador j : jugadores) {
                if (j.getNombre().equals(dto.getId())) {
                    if (dto.isTurno()) {
                        j.setTurno(true);
                        this.jugadorEnTurno = j;
                    } else {
                        j.setTurno(false);
                    }
                }
            }
        }
    }

    @Override
    public void ActualizarPuntos(PaqueteDTO paquete) {
        List<JugadorDTO> jugadoresDTO = convertirAListaJugadoresDTO(paquete.getContenido());
        System.out.println("[Partida] Puntos actualizados question mark");

        for (JugadorDTO dto : jugadoresDTO) {
            for (Jugador j : jugadores) {
                if (j.getNombre().equals(dto.getId())) {
                    j.setScore(dto.getScore());
                    System.out.println("[Partida] Score actualizado para " + j.getNombre() + ": " + dto.getScore());
                }
            }
        }
        // Notificar a los observadores para actualizar la vista
        for (ObservadorJugadores ob : observadoresJugadores) {
            ob.actualizar(jugadores);
        }
        notificarEventoRecibido("Puntos actualizados");
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        System.out.println("[Partida] evento recibido: " + paquete.getTipoEvento());
        TipoEvento tipo;

        try {
            tipo = TipoEvento.valueOf(paquete.getTipoEvento());
        } catch (IllegalArgumentException e) {
            notificarEventoRecibido("ERROR: Tipo de evento desconocido: " + paquete.getTipoEvento());
            return;
        }

        switch (tipo) {
            case NUEVA_LINEA: {
                NuevaLinea(paquete);
                break;
            }

            case TURNO_ACTUALIZADO: {
                TurnoActualizado(paquete);
                break;
            }

            case SOLICITAR_INICIAR_PARTIDA:

            case INICIO_PARTIDA: {
                InicioPartida();
                obtenerJugadorTurno(paquete);
                break;
            }

            case UNIRSE_PARTIDA:
                notificarEventoRecibido("Un jugador se unio a la partida");
                break;

            case ABANDONAR_PARTIDA:
                notificarEventoRecibido("Un jugador abandono la partida");
                break;

            case CONFIGURAR_PARTIDA:
                notificarEventoRecibido("Partida configurada");
                break;

            case SOLICITAR_FINALIZAR_PARTIDA:
                notificarEventoRecibido("Se solicito finalizar la partida");
                break;

            case ACTUALIZAR_PUNTOS:
                ActualizarPuntos(paquete);
                break;

            default:
                notificarEventoRecibido("Evento no manejado: " + tipo);
        }
    }

    public void setEmisor(IEmisor emisor) {
        this.emisor = emisor;
    }

    @Override
    public void notificarEventoRecibido(Object evento) {
        for (ObservadorEventos ob : observadoresEventos) {
            ob.actualizar(evento);
        }

    }

    @Override
    public void agregarObservadorEventos(ObservadorEventos ob) {
        this.observadoresEventos.add(ob);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPuertoOrigen(int puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    @Override
    public Jugador getJugadorSesion() {
        return jugadorSesion;
    }

    @Override
    public void setJugadorSesion(Jugador jugadorSesion) {
        this.jugadorSesion = jugadorSesion;
    }

    //metodos mapper
    /**
     * Convierte el contenido del paquete a PuntoDTO[].
     *
     */
    private PuntoDTO[] convertirAPuntosDTO(Object contenido) {
        if (contenido instanceof PuntoDTO[]) {
            return (PuntoDTO[]) contenido;
        }

        if (contenido instanceof List) {
            List<?> lista = (List<?>) contenido;
            PuntoDTO[] puntos = new PuntoDTO[lista.size()];

            for (int i = 0; i < lista.size(); i++) {
                Object item = lista.get(i);
                if (item instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) item;
                    int x = ((Number) map.get("x")).intValue();
                    int y = ((Number) map.get("y")).intValue();
                    puntos[i] = new PuntoDTO(x, y);
                }
            }
            return puntos;
        }

        return null;
    }

    /**
     * Convierte el contenido del paquete a JugadorDTO.
     *
     */
    private JugadorDTO convertirAJugadorDTO(Object contenido) {
        if (contenido instanceof JugadorDTO) {
            return (JugadorDTO) contenido;
        }

        if (contenido instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) contenido;
            String id = (String) map.get("id");
            boolean turno = map.get("turno") != null && (Boolean) map.get("turno");
            return new JugadorDTO(id, turno);
        }

        return null;
    }

    /**
     * Convierte el contenido del paquete a List de JugadorDTO.
     *
     */
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
                }
                resultado.add(dto);
            }
        }

        return resultado;
    }

}
