/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolicitudEntity;

import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;

/**
 * Clase que representa una solicitud para unirse a una partida. Contiene información del jugador solicitante, el estado de solicitud si es aceptada o rechazada y un tipo de rechazo. (El tipo de rechazo puede variar dependiendo si la partida en curso rechazo la solicitud ya que si esta en curso deberia de hacerlo )
 *
 * @author Jack Murrieta
 */
public class SolicitudUnirse {

    // Información de conexión del jugador solicitante
    private JugadorSolicitanteDTO jugadorSolicitante;

    private boolean solicitudEstado;

    private String tipoRechazo;

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
        this.solicitudEstado = false;
    }

    // Getters y Setters
    public JugadorSolicitanteDTO getJugadorSolicitante() {
        return jugadorSolicitante;
    }

    public String getTipoRechazo() {
        return tipoRechazo;
    }

    public void setTipoRechazo(String tipoRechazo) {
        this.tipoRechazo = tipoRechazo;
    }

    public void setJugadorSolicitante(JugadorSolicitanteDTO jugadorSolicitante) {
        this.jugadorSolicitante = jugadorSolicitante;
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
                + ", solicitudEstado=" + solicitudEstado
                + '}';
    }

}
