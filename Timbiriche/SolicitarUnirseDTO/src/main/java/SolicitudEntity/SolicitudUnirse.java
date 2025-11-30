/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolicitudEntity;

import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;

/**
 * Clase que representa una solicitud para unirse a una partida. Contiene información del jugador solicitante, el jugador host, y el estado de la solicitud.
 *
 * @author Jack Murrieta
 */
public class SolicitudUnirse {

    // Información de conexión del jugador solicitante (IP y puerto)
    private JugadorSolicitanteDTO jugadorSolicitante;

    // Jugador Host quien creo la partida
    private JugadorConfigDTO jugadorHost;

    // Estado de la solicitud: true = aceptada, false = rechazada/pendiente
    private boolean solicitudEstado;

    /**
     * Constructor por defecto.
     */
    public SolicitudUnirse() {
    }

    /**
     * Constructor completo para crear una solicitud de unirse.
     *
     * @param jugadorSolicitante Datos de conexión del solicitante
     * @param jugadorHost Jugador que es host de la partida
     */
    public SolicitudUnirse(JugadorSolicitanteDTO jugadorSolicitante,
            JugadorConfigDTO jugadorHost) {
        this.jugadorSolicitante = jugadorSolicitante;
        this.jugadorHost = jugadorHost;
        this.solicitudEstado = false;
    }

    // Getters y Setters
    public JugadorSolicitanteDTO getJugadorSolicitante() {
        return jugadorSolicitante;
    }

    public void setJugadorSolicitante(JugadorSolicitanteDTO jugadorSolicitante) {
        this.jugadorSolicitante = jugadorSolicitante;
    }

    public JugadorConfigDTO getJugadorHost() {
        return jugadorHost;
    }

    public void setJugadorHost(JugadorConfigDTO jugadorHost) {
        this.jugadorHost = jugadorHost;
    }

    public boolean isSolicitudEstado() {
        return solicitudEstado;
    }

    public void setSolicitudEstado(boolean solicitudEstado) {
        this.solicitudEstado = solicitudEstado;
    }

    @Override
    public String toString() {
        return "SolicitudUnirse{"
                + "jugadorSolicitante=" + jugadorSolicitante
                + ", jugadorHost=" + jugadorHost
                + ", solicitudEstado=" + solicitudEstado
                + '}';
    }
}
