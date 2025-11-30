/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloUnirsePartida;

import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import SolicitudEntity.SolicitudUnirse;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;

/**
 * Clase que maneja la lógica para unirse a una partida. Gestiona las solicitudes de jugadores que quieren unirse a una partida existente.
 *
 * @author Jack Murrieta
 */
public class UnirsePartida implements IUnirsePartida {

    //Se cambia por la variable deñ CU_configurarTablero
    private static final int MAX_JUGADORES = 4;
    
    private static final String ESTADO_EN_ESPERA = "EN_ESPERA";
    private static final String ESTADO_INICIADA = "INICIADA";
    private static final String ESTADO_FINALIZADA = "FINALIZADA";

    private JugadorConfigDTO jugadorHost;
    private SolicitudUnirse solicitudActual;
    private int numeroJugadores; // Número actual de jugadores en la partida
    private String estadoPartida; // Estado de la partida
    
    //enviar solicitud al eventBus
    private IEmisor emisorSolicitud;
    private IReceptor receptorSolicitud;

    /**
     * Constructor por defecto. Inicializa la partida en estado EN_ESPERA con 1 jugador (el host).
     */
    public UnirsePartida() {
        this.numeroJugadores = 1; // El host cuenta como un jugador
        this.estadoPartida = ESTADO_EN_ESPERA;
    }

    /**
     * Constructor con jugador host.
     *
     * @param jugadorHost El jugador que crea la partida
     * @param receptorSolicitud
     */
    public UnirsePartida(JugadorConfigDTO jugadorHost,IReceptor receptorSolicitud) {
        this();
        this.jugadorHost = jugadorHost;
        if (this.jugadorHost != null) {
            this.jugadorHost.setEsHost(true);
        }
        this.receptorSolicitud = receptorSolicitud;
    }

    /**
     * Constructor completo.
     *
     * @param jugadorHost El jugador host
     * @param numeroJugadores Número inicial de jugadores
     * @param estadoPartida Estado inicial de la partida
     */
    public UnirsePartida(JugadorConfigDTO jugadorHost, int numeroJugadores, String estadoPartida) {
        this.jugadorHost = jugadorHost;
        if (this.jugadorHost != null) {
            this.jugadorHost.setEsHost(true);
        }
        this.numeroJugadores = numeroJugadores;
        this.estadoPartida = estadoPartida;
    }

    @Override
    public SolicitudUnirse crearSolicitud(JugadorSolicitanteDTO jugadorSolicitanteDTO) {
        // Validar que la partida esté en estado EN_ESPERA
        if (!ESTADO_EN_ESPERA.equals(this.estadoPartida)) {
            throw new IllegalStateException("No se puede crear una solicitud. La partida ya ha iniciado o finalizado.");
        }

        // Validar que haya espacio disponible
        if (!validarEspacioJugador()) {
            throw new IllegalStateException("No se puede crear una solicitud. La partida está llena (máximo 4 jugadores).");
        }

        // Validar que exista un jugador host
        if (this.jugadorHost == null) {
            throw new IllegalStateException("No existe un jugador host en la partida.");
        }

        // Crear la solicitud con el jugador solicitante y el host
        SolicitudUnirse solicitud =new SolicitudUnirse(jugadorSolicitanteDTO, jugadorHost);

        return this.solicitudActual;
    }
    
    

    @Override
    public JugadorConfigDTO obtenerJugadorHost() {
        if (this.jugadorHost == null) {
            throw new IllegalStateException("No existe un jugador host configurado.");
        }
        return this.jugadorHost;
    }

    @Override
    public boolean validarEspacioJugador() {
        return this.numeroJugadores < MAX_JUGADORES;
    }

    @Override
    public String obtenerEstadoPartida() {
        return this.estadoPartida;
    }

    @Override
    public void cambiarEstadoSolicitud(SolicitudUnirse solicitud, boolean aceptada) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser null");
        }

        // Cambiar el estado de la solicitud
        solicitud.setSolicitudEstado(aceptada);

        // Si la solicitud fue aceptada, incrementar el número de jugadores
        if (aceptada) {
            if (validarEspacioJugador()) {
                this.numeroJugadores++;
                System.out.println("Solicitud aceptada. Número de jugadores: " + this.numeroJugadores);
            } else {
                //mandar el msj a modelo
                throw new IllegalStateException("No se puede aceptar la solicitud. La partida está llena.");
            }
        } else {
            //mandar el msj de rechazado a modelo
            System.out.println("Solicitud rechazada.");
        }

        // Actualizar la solicitud actual si es la misma
        if (this.solicitudActual != null && this.solicitudActual.equals(solicitud)) {
            this.solicitudActual = solicitud;
            //si la solicitud fue aceptada que entre al frmSala espera
        }
    }

    @Override
    public void setJugadorHost(JugadorConfigDTO jugadorHost) {
        this.jugadorHost = jugadorHost;
        if (this.jugadorHost != null) {
            this.jugadorHost.setEsHost(true);
        }
    }

    @Override
    public void setNumeroJugadores(int numeroJugadores) {
        if (numeroJugadores < 1) {
            throw new IllegalArgumentException("El número de jugadores debe ser al menos 1.");
        }
        if (numeroJugadores > MAX_JUGADORES) {
            throw new IllegalArgumentException("El número de jugadores no puede exceder " + MAX_JUGADORES + ".");
        }
        this.numeroJugadores = numeroJugadores;
    }

    @Override
    public void setEstadoPartida(String estado) {
        // Validar que el estado sea válido
        if (!ESTADO_EN_ESPERA.equals(estado)
                && !ESTADO_INICIADA.equals(estado)
                && !ESTADO_FINALIZADA.equals(estado)) {
            throw new IllegalArgumentException("Estado inválido. Use: EN_ESPERA, INICIADA o FINALIZADA.");
        }
        this.estadoPartida = estado;
    }

    // Getters adicionales
    /**
     * Obtiene el número actual de jugadores.
     *
     * @return Número de jugadores
     */
    public int getNumeroJugadores() {
        return numeroJugadores;
    }

    /**
     * Obtiene la solicitud actual.
     *
     * @return SolicitudUnirse actual
     */
    public SolicitudUnirse getSolicitudActual() {
        return solicitudActual;
    }

    /**
     * Verifica si la partida está llena (4 jugadores).
     *
     * @return true si está llena
     */
    public boolean isPartidaLlena() {
        return this.numeroJugadores >= MAX_JUGADORES;
    }

    /**
     * Verifica si la partida ha iniciado.
     *
     * @return true si ha iniciado
     */
    public boolean isPartidaIniciada() {
        return ESTADO_INICIADA.equals(this.estadoPartida);
    }

    /**
     * Obtiene el máximo de jugadores permitidos.
     *
     * @return Máximo de jugadores
     */
    public static int getMaxJugadores() {
        return MAX_JUGADORES;
    }
}
