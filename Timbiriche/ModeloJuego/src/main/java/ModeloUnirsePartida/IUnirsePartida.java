/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida;

import DTO.JugadorSolicitanteDTO;
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

    //envia la solicitud por el Iemisor
    public void enviarSolicitudJugadorHost(SolicitudUnirse solicitud);

    public void enviarRespuestaSolicitud(SolicitudUnirse solicitud);

}
