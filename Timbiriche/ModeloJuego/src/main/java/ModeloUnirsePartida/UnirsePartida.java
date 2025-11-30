/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloUnirsePartida;

import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import SolicitudEntity.SolicitudUnirse;
import java.util.ArrayList;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 * Clase que maneja la lógica para unirse a una partida. Gestiona las solicitudes de jugadores que quieren unirse a una partida existente.
 *
 * @author Jack Murrieta
 */
public class UnirsePartida implements IUnirsePartida, IPublicadorSolicitud {

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

    //enviar a MVC UnirsePartida cambio
    private List<INotificadorSolicitud> notificados = new ArrayList<>();

    // Configuración de red para EventBus (igual que en Partida)
    private String host;
    private int puertoOrigen;
    private int puertoDestino;
    
    public UnirsePartida() {
    }
    
    public UnirsePartida(IEmisor emisorSolicitud, IReceptor receptorSolicitud) {
        this.emisorSolicitud = emisorSolicitud;
        this.receptorSolicitud = receptorSolicitud;
    }
    
    public void setSolicitudActual(SolicitudUnirse solicitudActual) {
        this.solicitudActual = solicitudActual;
    }
    
    public void setEmisorSolicitud(IEmisor emisorSolicitud) {
        this.emisorSolicitud = emisorSolicitud;
    }
    
    public void setReceptorSolicitud(IReceptor receptorSolicitud) {
        this.receptorSolicitud = receptorSolicitud;
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
        SolicitudUnirse solicitud = new SolicitudUnirse(jugadorSolicitanteDTO, jugadorHost);
        
        return this.solicitudActual;
    }
    
    @Override
    public void enviarSolicitudJugadorHost(SolicitudUnirse solicitud) {
        // Enviar evento SOLICITAR_UNIRSE al EventBus
        // Este evento solo será recibido por el Host (único suscrito)
        PaqueteDTO paquete = new PaqueteDTO(solicitud, "SOLICITAR_UNIRSE");

        // Configurar campos de red (igual que en Partida)
        paquete.setHost(this.host);
        paquete.setPuertoOrigen(this.puertoOrigen);
        paquete.setPuertoDestino(this.puertoDestino);

        emisorSolicitud.enviarCambio(paquete);

        System.out.println("✓ Solicitud enviada al EventBus con evento SOLICITAR_UNIRSE");
        System.out.println("  → Host: " + this.host + ", Puerto origen: " + this.puertoOrigen + ", Puerto destino: " + this.puertoDestino);
    }

    /**
     * Envía la respuesta de la solicitud al solicitante. Este método es usado por el Host para responder.
     *
     * @param solicitud La solicitud con el estado actualizado (aceptada/rechazada)
     */
    @Override
    public void enviarRespuestaSolicitud(SolicitudUnirse solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        // Enviar evento RESPUESTA_SOLICITUD al EventBus
        // Este evento solo será recibido por el Solicitante (único suscrito)
        PaqueteDTO paquete = new PaqueteDTO(solicitud, "RESPUESTA_SOLICITUD");

        // Configurar campos de red (igual que en Partida)
        paquete.setHost(this.host);
        paquete.setPuertoOrigen(this.puertoOrigen);
        paquete.setPuertoDestino(this.puertoDestino);

        emisorSolicitud.enviarCambio(paquete);

        String estado = solicitud.isSolicitudEstado() ? "ACEPTADA" : "RECHAZADA";
        System.out.println("✓ Respuesta enviada al EventBus: " + estado);
        System.out.println("  → Host: " + this.host + ", Puerto origen: " + this.puertoOrigen + ", Puerto destino: " + this.puertoDestino);
    }
    
    @Override
    public void cambiarEstadoSolicitud(SolicitudUnirse solicitud, boolean aceptada) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser null");
        }

        // Cambiar el estado de la solicitud
        solicitud.setSolicitudEstado(aceptada);
        
        //observer para modeloJuego
        notificar(solicitud);
    }

    /**
     * Obtiene la solicitud actual.
     *
     * @return SolicitudUnirse actual
     */
    @Override
    public SolicitudUnirse getSolicitudActual() {
        return solicitudActual;
    }
    
    public JugadorConfigDTO obtenerJugadorHost() {
        if (this.jugadorHost == null) {
            throw new IllegalStateException("No existe un jugador host configurado.");
        }
        return this.jugadorHost;
    }
    
    public boolean validarEspacioJugador() {
        return this.numeroJugadores < MAX_JUGADORES;
    }
    
    public String obtenerEstadoPartida() {
        return this.estadoPartida;
    }
    
    public void setJugadorHost(JugadorConfigDTO jugadorHost) {
        this.jugadorHost = jugadorHost;
        if (this.jugadorHost != null) {
            this.jugadorHost.setEsHost(true);
        }
    }
    
    public void setNumeroJugadores(int numeroJugadores) {
        if (numeroJugadores < 1) {
            throw new IllegalArgumentException("El número de jugadores debe ser al menos 1.");
        }
        if (numeroJugadores > MAX_JUGADORES) {
            throw new IllegalArgumentException("El número de jugadores no puede exceder " + MAX_JUGADORES + ".");
        }
        this.numeroJugadores = numeroJugadores;
    }
    
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
    
    @Override
    public void agregarNotificador(INotificadorSolicitud notificador) {
        notificados.add(notificador);
    }

    @Override
    public void notificar(SolicitudUnirse solicitud) {
        for (INotificadorSolicitud notificado : notificados) {
            notificado.actualizar(solicitud);

        }
    }

    // Setters para configuración de red (igual que en Partida)
    public void setHost(String host) {
        this.host = host;
    }

    public void setPuertoOrigen(int puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    public String getHost() {
        return host;
    }

    public int getPuertoOrigen() {
        return puertoOrigen;
    }

    public int getPuertoDestino() {
        return puertoDestino;
    }

}
