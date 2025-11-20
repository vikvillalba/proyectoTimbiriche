package Fachada;

import Entidades.Cuadro;
import Entidades.Jugador;
import Entidades.Linea;
import Entidades.Punto;
import Entidades.Tablero;
import Entidades.TipoEvento;
import Fachada.PartidaFachada;
import Mapper.MapperJugadores;
import Observer.ObservadorEventos;
import Observer.ObservadorInicio;
import Observer.ObservadorJugadores;
import excepciones.PartidaExcepcion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PaqueteDTO;

/**
 * Clase que representa una partida de juego. Engloba a todas las clases
 * necesarias para iniciar una partida.
 *
 * @author victoria
 */
public class Partida implements PartidaFachada, IReceptor {

    private Tablero tablero;

    private ObservadorInicio observadorInicioJuego;
    private List<Jugador> jugadores;
    private Jugador jugadorEnTurno;
    private IEmisor emisor;
    private MapperJugadores mapperJugadores;


    private List<ObservadorJugadores> observadoresJugadores = new ArrayList<>();
    private List<ObservadorEventos<?>> observadoresEventos = new ArrayList<>();
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
        Punto[] puntos = new Punto[]{origen, destino};
        PaqueteDTO paquete = new PaqueteDTO(puntos, TipoEvento.NUEVA_LINEA.toString());
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
        observadorInicioJuego.iniciarJuego();
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
    public void agregarObservadorEventos(ObservadorEventos<?> ob) {
        this.observadoresEventos.add(ob);
    }

    @Override
    public void notificarEventoRecibido(Object evento) {
        for (ObservadorEventos ob : observadoresEventos) {
            ob.actualizar(evento);
        }
    }

    @Override
    public void actualizarTurno() {
        JugadorDTO jugadorEnTurnoDTO = mapperJugadores.toDTO(jugadorEnTurno);
        PaqueteDTO paquete = new PaqueteDTO(jugadorEnTurnoDTO, "ACTUALIZAR_TURNO");
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

        TipoEvento tipo;

        try {
            tipo = TipoEvento.valueOf(paquete.getTipoEvento());
        } catch (IllegalArgumentException e) {
            notificarEventoRecibido("ERROR: Tipo de evento desconocido: " + paquete.getTipoEvento());
            return;
        }

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

                    // Notificar que se recibió un movimiento válido
                    notificarEventoRecibido("Línea agregada: " + origen + " → " + destino);

                    // Si no hizo cuadro, actualizar turno
                    if (!hizoCuadro) {
                        actualizarTurno();
                    }
                } catch (PartidaExcepcion e) {
                    notificarEventoRecibido(e);
                }

                break;
            }

            case TURNO_ACTUALIZADO:
                JugadorDTO jugadorTurnoDTO = (JugadorDTO) paquete.getContenido();
                Jugador jugadorTurno = mapperJugadores.toEntidad(jugadorTurnoDTO);

                if (jugadorTurno != null) {
                    jugadorEnTurno = jugadorTurno;
                    notificarEventoRecibido("Turno actualizado: " + jugadorEnTurno.getNombre());
                }
                notificarEventoRecibido("TURNO_ACTUALIZADO");
                break;

            case SOLICITAR_INICIAR_PARTIDA:

            case INICIO_PARTIDA:
                notificarObservadorInicioJuego();
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
}
