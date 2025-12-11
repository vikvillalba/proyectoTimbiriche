/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloUnirsePartida;

import ModeloUnirsePartida.Observadores.IPublicadorSolicitud;
import ModeloUnirsePartida.Observadores.INotificadorSolicitud;
import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import ModeloUnirsePartida.Observadores.INotificadorConsenso;
import ModeloUnirsePartida.Observadores.IPublicadorConsenso;
import SolicitudEntity.SolicitudUnirse;
import java.util.ArrayList;
import java.util.List;
import ModeloUnirsePartida.Observadores.IPublicadorJugadorEncontrado;
import ModeloUnirsePartida.Observadores.INotificadorJugadorEncontrado;

/**
 * Clase que maneja la lógica para unirse a una partida. Gestiona las solicitudes de jugadores que quieren unirse a una partida existente.
 *
 * @author Jack Murrieta
 */
public class UnirsePartida implements IUnirsePartida, IPublicadorSolicitud, IPublicadorJugadorEncontrado,
        IPublicadorConsenso {

    private JugadorConfigDTO jugadorEnSala;
    private JugadorSolicitanteDTO jugadorSolicitante;
    private SolicitudUnirse solicitudActual;
    private Fachada.Partida partida; // Referencia a la partida real

    //enviar a MVC UnirsePartida cambio
    private List<INotificadorSolicitud> notificados = new ArrayList<>();

    INotificadorJugadorEncontrado notificadorJugadorEncontrado;// MODELO ARRANQUE
    INotificadorConsenso notificadorConsenso; //FRMALAESPERA

    public UnirsePartida() {
    }

    public void setSolicitudActual(SolicitudUnirse solicitudActual) {
        this.solicitudActual = solicitudActual;
    }

    @Override
    public SolicitudUnirse crearSolicitud(JugadorSolicitanteDTO jugadorSolicitanteDTO) {

        if (this.jugadorEnSala == null) {
            //notificar a modeloUnirsePartida de esto
            notificarJugadorEncontrado(jugadorEnSala);
            return null;
        }
        //validar que no sea null

        SolicitudUnirse solicitud = new SolicitudUnirse(jugadorSolicitanteDTO);

        this.solicitudActual = solicitud;

        return solicitud;
    }

    /**
     * Cambia el estado de una solicitud y notifica a los observadores.
     *
     * @param solicitud La solicitud a actualizarJugadorEncontrado
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

    public void setJugadorEnSala(JugadorConfigDTO jugadorEnSala) {
        this.jugadorEnSala = jugadorEnSala;
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
            notificado.actualizarSolicitudUnirse(solicitud);

        }
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
    public void agregarNotificadorJugadorEncontrado(INotificadorJugadorEncontrado notificador) {
        this.notificadorJugadorEncontrado = notificador;
    }

    /**
     * Notifica al ModeloArranque que se encontró (o no) un host. Implementa PublicadorHostEncontrado.
     *
     * @param jugador El jugador host encontrado (puede ser null)
     */
    @Override
    public void notificarJugadorEncontrado(JugadorConfigDTO jugador) {
        notificadorJugadorEncontrado.actualizarJugadorEncontrado(jugador);
    }

    @Override
    public void agregarNotificadorConsenso(INotificadorConsenso notificador) {
        notificadorConsenso = notificador;

    }

    @Override
    public void notificarConsensoFinalizado(boolean aceptado, String tipoRechazo) {
        notificadorConsenso.actualizarConsensoFinalizado(aceptado, tipoRechazo);
    }

    @Override
    public JugadorConfigDTO getJugadorEnSala() {
        return jugadorEnSala;
    }

}
