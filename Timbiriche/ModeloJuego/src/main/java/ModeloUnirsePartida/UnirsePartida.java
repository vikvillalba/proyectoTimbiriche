/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloUnirsePartida;

import ModeloUnirsePartida.Observadores.IPublicadorHostEncontrado;
import ModeloUnirsePartida.Observadores.IPublicadorSolicitud;
import ModeloUnirsePartida.Observadores.INotificadorSolicitud;
import ModeloUnirsePartida.Observadores.INotificadorHostEncontrado;
import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import SolicitudEntity.SolicitudUnirse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 * Clase que maneja la lógica para unirse a una partida. Gestiona las solicitudes de jugadores que quieren unirse a una partida existente.
 *
 * @author Jack Murrieta
 */
public class UnirsePartida implements IUnirsePartida, IPublicadorSolicitud, IPublicadorHostEncontrado {

    // Constantes de estado
    private static final String ESTADO_EN_ESPERA = "EN_ESPERA";
    private static final String ESTADO_INICIADA = "INICIADA";
    private static final String ESTADO_FINALIZADA = "FINALIZADA";

    private JugadorConfigDTO jugadorHost;
    private JugadorSolicitanteDTO jugadorSolicitante;
    private SolicitudUnirse solicitudActual;
    private Fachada.Partida partida; // Referencia a la partida real

    //enviar solicitud al eventBus
    private IEmisor emisorSolicitud;
    private IReceptor receptorSolicitud;

    //enviar a MVC UnirsePartida cambio
    private List<INotificadorSolicitud> notificados = new ArrayList<>();

    // Configuración de red para EventBus
    private int puertoOrigen;
    private int puertoDestino;

    //notificadorHost encontrado a modeloArranque 
    INotificadorHostEncontrado modeloArranque;

    public UnirsePartida() {
    }

    public UnirsePartida(IEmisor emisorSolicitud) {
        this.emisorSolicitud = emisorSolicitud;
    }

    public void setSolicitudActual(SolicitudUnirse solicitudActual) {
        this.solicitudActual = solicitudActual;
    }

    public void setReceptorSolicitud(IReceptor receptorSolicitud) {
        this.receptorSolicitud = receptorSolicitud;
    }

    public void setEmisorSolicitud(IEmisor emisorSolicitud) {
        this.emisorSolicitud = emisorSolicitud;
    }

    /**
     * Solicita al EventBus que envíe la información del host de la partida.
     *
     * @param jugador El jugador que solicita el host
     */
    @Override
    public void solicitarHost(JugadorSolicitanteDTO jugador) {
        this.jugadorSolicitante = jugador;

        PaqueteDTO paquete = new PaqueteDTO();
        paquete.setContenido("SOLICITUD_HOST");
        paquete.setTipoEvento("OBTENER_HOST");
        paquete.setHost(this.jugadorSolicitante.getIp());
        paquete.setPuertoOrigen(this.jugadorSolicitante.getPuerto());
        paquete.setPuertoDestino(puertoDestino);

        emisorSolicitud.enviarCambio(paquete);
    }

    @Override
    public SolicitudUnirse crearSolicitud(JugadorSolicitanteDTO jugadorSolicitanteDTO) {

        if (this.jugadorHost == null) {
            throw new IllegalStateException("No existe un jugador host en la partida.");
        }

        SolicitudUnirse solicitud
                = new SolicitudUnirse(jugadorSolicitanteDTO, jugadorHost);

        this.solicitudActual = solicitud;

        return solicitud;
    }

    @Override
    public void enviarSolicitudSalaEspera(SolicitudUnirse solicitud) {
        // Enviar evento SOLICITAR_UNIRSE al EventBus
        // Este evento será recibido por TODOS los jugadores en sala de espera
        PaqueteDTO paquete = new PaqueteDTO(solicitud, "SOLICITAR_UNIRSE");

        paquete.setHost(solicitud.getJugadorSolicitante().getIp());
        paquete.setPuertoOrigen(this.puertoOrigen);
        paquete.setPuertoDestino(this.puertoDestino);

        System.out.println("[UnirsePartida] Enviando SOLICITAR_UNIRSE a todos los jugadores en sala");
        emisorSolicitud.enviarCambio(paquete);
    }

    /**
     * Envía un voto (aceptar/rechazar) para una solicitud al EventBus. Este método es llamado por cada jugador en sala de espera.
     *
     * @param solicitud La solicitud con el voto (aceptada/rechazada)
     */
    @Override
    public void enviarVotoSolicitud(SolicitudUnirse solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        String estadoVoto = solicitud.isSolicitudEstado() ? "ACEPTADO" : "RECHAZADO";
        System.out.println("[UnirsePartida] Enviando voto: " + estadoVoto);

        // Enviar evento VOTAR_SOLICITUD al EventBus
        PaqueteDTO paquete = new PaqueteDTO(solicitud, "VOTAR_SOLICITUD");

        paquete.setHost(this.jugadorHost != null ? this.jugadorHost.getIp() : "localhost");
        paquete.setPuertoOrigen(this.puertoOrigen);
        paquete.setPuertoDestino(this.puertoDestino);

        emisorSolicitud.enviarCambio(paquete);

        System.out.println("[UnirsePartida] Voto enviado al EventBus");
    }

    /**
     * Envía la respuesta de una solicitud al cliente solicitante vía EventBus.
     *
     * @param solicitud La solicitud con el estado actualizado (aceptada/rechazada)
     */
//    @Override
//    public void enviarRespuestaSolicitud(SolicitudUnirse solicitud) {
//        if (solicitud == null) {
//            throw new IllegalArgumentException("La solicitud no puede ser nula");
//        }
//
//        String estado = solicitud.isSolicitudEstado() ? "ACEPTADA" : "RECHAZADA";
//        System.out.println("[UnirsePartida] Enviando RESPUESTA_SOLICITUD al cliente. Estado: " + estado);
//        System.out.println("[UnirsePartida] Solicitante: " + solicitud.getJugadorSolicitante().getIp() + ":" + solicitud.getJugadorSolicitante().getPuerto());
//
//        // Enviar evento RESPUESTA_SOLICITUD al EventBus
//        // Este evento solo será recibido por el Solicitante (único suscrito)
//        PaqueteDTO paquete = new PaqueteDTO(solicitud, "RESPUESTA_SOLICITUD");
//
//        // Configurar campos de red (igual que en Partida)
//        paquete.setHost(solicitud.getJugadorHost().getIp());
//        paquete.setPuertoOrigen(this.puertoOrigen);
//        paquete.setPuertoDestino(this.puertoDestino);
//
//        emisorSolicitud.enviarCambio(paquete);
//
//        System.out.println("[UnirsePartida] Paquete RESPUESTA_SOLICITUD enviado al EventBus");
//    }
    /**
     * Cambia el estado de una solicitud y notifica a los observadores.
     *
     * @param solicitud La solicitud a actualizar
     * @param aceptada true si se acepta, false si se rechaza
     */
    @Override
    public void cambiarEstadoSolicitud(SolicitudUnirse solicitud, boolean aceptada) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser null");
        }

        String estado = aceptada ? "ACEPTADA" : "RECHAZADA";
        System.out.println("[UnirsePartida] cambiarEstadoSolicitud() - Estado: " + estado);

        // Cambiar el estado de la solicitud
        solicitud.setSolicitudEstado(aceptada);
        //si la solicitud es false
        if (!aceptada) {
            solicitud.setTipoRechazo(solicitud.getTipoRechazo());
            System.out.println("[UnirsePartida] Tipo de rechazo: " + solicitud.getTipoRechazo());
        }

        //observer para modeloJuego - Esto notifica a FrmSalaEspera
        System.out.println("[UnirsePartida] Notificando cambio de estado a observadores...");
        notificarSolicitud(solicitud);
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

    public boolean validarEspacioJugador() {
        if (this.partida == null) {
            return false;
        }
        return !this.partida.isPartidaLlena();
    }

    public String obtenerEstadoPartida() {
        if (this.partida == null) {
            return null;
        }
        return this.partida.getEstadoPartida();
    }

    public void setJugadorHost(JugadorConfigDTO jugadorHost) {
        this.jugadorHost = jugadorHost;
        if (this.jugadorHost != null) {
            this.jugadorHost.setEsHost(true);
        }
    }

    @Override
    public void setPartida(Fachada.Partida partida) {
        this.partida = partida;
    }

    public Fachada.Partida getPartida() {
        return this.partida;
    }

    // Getters adicionales
    /**
     * Obtiene el número actual de jugadores.
     *
     * @return Número de jugadores
     */
    public int getNumeroJugadores() {
        if (this.partida == null) {
            return 0;
        }
        return this.partida.getJugadores().size();
    }

    /**
     * Verifica si la partida está llena.
     *
     * @return true si está llena
     */
    public boolean isPartidaLlena() {
        if (this.partida == null) {
            return false;
        }
        return this.partida.isPartidaLlena();
    }

    /**
     * Verifica si la partida ha iniciado.
     *
     * @return true si ha iniciado
     */
    public boolean isPartidaIniciada() {
        if (this.partida == null) {
            return false;
        }
        return this.partida.isPartidaIniciada();
    }

    /**
     * Verifica si la partida ha finalizado.
     *
     * @return true si ha finalizado
     */
    public boolean isPartidaFinalizada() {
        if (this.partida == null) {
            return false;
        }
        return this.partida.isPartidaFinalizada();
    }

    /**
     * Obtiene el máximo de jugadores permitidos.
     *
     * @return Máximo de jugadores
     */
    public int getMaxJugadores() {
        if (this.partida == null) {
            return 0;
        }
        return this.partida.getMaxJugadores();
    }

    /**
     * Determina el tipo de rechazo basado en el estado actual de la partida. Este método puede ser usado por el Host para determinar por qué rechazar una solicitud.
     *
     * @return String con el tipo de rechazo
     */
    public String asignarTipoRechazo() {
        if (this.partida == null) {
            return "RECHAZADO_POR_HOST"; // No hay partida configurada
        }

        // Verificar si la partida está llena
        if (this.partida.isPartidaLlena()) {
            return "PARTIDA_LLENA";
        }

        // Verificar si la partida ya ha iniciado
        if (this.partida.isPartidaIniciada()) {
            return "PARTIDA_INICIADA";
        }

        // Verificar si la partida ha finalizado
        if (this.partida.isPartidaFinalizada()) {
            return "PARTIDA_FINALIZADA";
        }

        // Rechazo genérico (por ejemplo, por decisión manual del host)
        return "RECHAZADO_POR_HOST";
    }

    /**
     * Rechaza una solicitud estableciendo su estado y tipo de rechazo.
     *
     * @param solicitud La solicitud a rechazar
     */
    public void rechazarSolicitud(SolicitudUnirse solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        String tipoRechazo = asignarTipoRechazo();
        solicitud.setSolicitudEstado(false);
        solicitud.setTipoRechazo(tipoRechazo);

        System.out.println("[UnirsePartida] Solicitud rechazada - Tipo: " + tipoRechazo);
    }

    @Override
    public void agregarNotificadorSolicitud(INotificadorSolicitud notificador) {
        notificados.add(notificador);
    }

    @Override
    public void notificarSolicitud(SolicitudUnirse solicitud) {
        for (INotificadorSolicitud notificado : notificados) {
            notificado.actualizar(solicitud);

        }
    }

    public void setPuertoOrigen(int puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    public int getPuertoOrigen() {
        return puertoOrigen;
    }

    public int getPuertoDestino() {
        return puertoDestino;
    }

    public JugadorSolicitanteDTO getJugadorSolicitante() {
        return jugadorSolicitante;
    }

    @Override
    public void setJugadorSolicitante(JugadorSolicitanteDTO jugadorSolicitante) {
        this.jugadorSolicitante = jugadorSolicitante;
    }

    //NOTIFICAR HOST A MODELO ARRANQUE
    @Override
    public void agregarNotificadorHostEncontrado(INotificadorHostEncontrado notificador) {
        this.modeloArranque = notificador;
    }

    /**
     * Notifica al ModeloArranque que se encontró (o no) un host. Implementa PublicadorHostEncontrado.
     *
     * @param jugador El jugador host encontrado (puede ser null)
     */
    @Override
    public void notificarHostEncontrado(JugadorConfigDTO jugador) {
        modeloArranque.actualizar(jugador);
    }

    /**
     * Suscribe a un jugador a los eventos de sala de espera. Debe llamarse cuando un jugador se une exitosamente a una partida. Esto permite que reciba solicitudes de nuevos jugadores y pueda votar.
     */
    @Override
    public void suscribirseASalaEspera() {
        List<String> eventosNuevos = Arrays.asList(
                "EN_SALA_ESPERA",
                "SOLICITAR_UNIRSE"
        );

        PaqueteDTO registroEventBus = new PaqueteDTO(eventosNuevos, "INICIAR_CONEXION");
        registroEventBus.setHost(this.jugadorSolicitante != null
                ? this.jugadorSolicitante.getIp()
                : "localhost");
        registroEventBus.setPuertoOrigen(this.puertoOrigen);
        registroEventBus.setPuertoDestino(this.puertoDestino);

        System.out.println("[UnirsePartida] Suscribiéndose a eventos de sala de espera: " + eventosNuevos);
        emisorSolicitud.enviarCambio(registroEventBus);
    }
//
//    /**
//     * Desuscribe a un jugador de los eventos de sala de espera.
//     * Debe llamarse cuando un jugador abandona la sala o cuando inicia la partida.
//     */
//    public void desuscribirseASalaEspera() {
//        // TODO: Implementar lógica de desuscripción si es necesaria
//        System.out.println("[UnirsePartida] Jugador se desuscribe de sala de espera");
//    }

}
