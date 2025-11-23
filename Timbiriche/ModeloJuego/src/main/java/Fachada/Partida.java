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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import excepciones.PartidaExcepcion;
import java.util.ArrayList;
import java.util.List;
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
//    private ManejadorTurnos turnos;
//    private ObservadorTurnos observadorTurnos;

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
        this.jugadorEnTurno = jugadores.get(0);

//        this.turnos = new ManejadorTurnos(this.jugadores); //jugadores con turnos asignados
        // Registrar la partida como observador de turnos (para notificar al modelo cuando se avance de turno)
//        this.turnos.agregarObservadorTurnos(this);
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
        return tablero.unirPuntos(origen, destino, jugadorEnTurno);

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
//        turnos.actualizarTurno();
//        notificarObservadorTurnos();
    }

//    public void notificarObservadorTurnos() {
//        if (observadorTurnos != null) {
//            observadorTurnos.actualizar(jugadores);
//        }
//    }
//    public void agregarObservadorTurnos(ObservadorTurnos ob) {
//        this.observadorTurnos = ob;
//    }
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
        Gson gson = new Gson();
        switch (tipo) {

            case NUEVA_LINEA: {
                // Se espera un PaqueteDTO<Punto[]>
                Punto[] puntos;

                try {
                    puntos = (Punto[]) paquete.getContenido();
                } catch (ClassCastException e) {
                    notificarEventoRecibido("ERROR: contenido no es Punto[]");
                    return;
                }

                if (puntos == null || puntos.length != 2) {
                    notificarEventoRecibido("ERROR: Punto[] inválido en NUEVA_LINEA");
                    return;
                }

                Punto origen = puntos[0];
                Punto destino = puntos[1];

                try {
                    boolean hizoCuadro = validarPuntos(origen, destino);

                    notificarEventoRecibido("Línea agregada: " + origen + " - " + destino);

                    if (!hizoCuadro) {
                        actualizarTurno();
                    }
                    notificarObservadorJugadores();

                } catch (PartidaExcepcion e) {
                    notificarEventoRecibido(e);
                }

                break;
            }

            case TURNO_ACTUALIZADO:
                JugadorDTO jugadorTurnoDTO = gson.fromJson(
                        gson.toJson(paquete.getContenido()),
                        JugadorDTO.class
                );

                System.out.println("[Partida] Jugador en turno según DTO: " + jugadorTurnoDTO.getId());

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
                break;

            case SOLICITAR_INICIAR_PARTIDA:

            case INICIO_PARTIDA:
                List<JugadorDTO> jugadoresDTO = gson.fromJson(
                        gson.toJson(paquete.getContenido()),
                        new TypeToken<List<JugadorDTO>>() {
                        }.getType()
                );

                for (JugadorDTO dto : jugadoresDTO) {
                    for (Jugador j : jugadores) {
                        if (j.getNombre().equals(dto.getId())) {
                            if (dto.isTurno() == true) {
                                j.setTurno(true);
                                this.jugadorEnTurno = j;
                            } else {
                                j.setTurno(false);
                            }
                        }
                    }
                }
                notificarObservadorInicioJuego();
                notificarObservadorJugadores();
                notificarEventoRecibido("Partida iniciada");
                break;

            case UNIRSE_PARTIDA:
                notificarEventoRecibido("Un jugador se unió a la partida");
                break;

            case ABANDONAR_PARTIDA:
                notificarEventoRecibido("Un jugador abandonó la partida");
                break;

            case CONFIGURAR_PARTIDA:
                notificarEventoRecibido("Partida configurada");
                break;

            case SOLICITAR_FINALIZAR_PARTIDA:
                notificarEventoRecibido("Se solicitó finalizar la partida");
                break;

            case ACTUALIZAR_PUNTOS:
                notificarEventoRecibido("Se actualizaron los puntos");
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

    public void setJugadorSesion(Jugador jugadorSesion) {
        this.jugadorSesion = jugadorSesion;
    }

}
