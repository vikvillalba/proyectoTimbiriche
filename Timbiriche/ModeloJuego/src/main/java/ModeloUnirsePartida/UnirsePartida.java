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

    public void setJugadorEnSala(JugadorConfigDTO jugadorEnSala) {
        this.jugadorEnSala = jugadorEnSala;
    }

    @Override
    public void setPartida(Fachada.Partida partida) {
        this.partida = partida;
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
