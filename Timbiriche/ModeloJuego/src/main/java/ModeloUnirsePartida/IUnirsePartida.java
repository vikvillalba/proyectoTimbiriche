/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida;

import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import Fachada.Partida;
import SolicitudEntity.SolicitudUnirse;

/**
 * Interfaz fachada que usara modelo
 *
 * @author Jack Murrieta
 */
public interface IUnirsePartida {

    public SolicitudUnirse crearSolicitud(JugadorSolicitanteDTO jugadorSolicitanteDTO);

    public void cambiarEstadoSolicitud(SolicitudUnirse solicitud, boolean aceptada);

    //obtener solicitud
    public SolicitudUnirse getSolicitudActual();

    public void setJugadorSolicitante(JugadorSolicitanteDTO jugadorSolicitante);

    public JugadorSolicitanteDTO getJugadorSolicitante();

    public JugadorConfigDTO getJugadorEnSala();

    public void setPartida(Partida partida);

}
