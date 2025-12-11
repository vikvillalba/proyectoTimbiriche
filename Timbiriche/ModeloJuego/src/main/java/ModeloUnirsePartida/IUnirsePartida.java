/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida;

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

    //envia la solicitud por el Iemisor
    public void enviarSolicitudSalaEspera(SolicitudUnirse solicitud);

    //respuesta de un jugador en la sala de espera true o false
    public void enviarVotoSolicitud(SolicitudUnirse solicitud);

    //metodo de enviar una peticion al eventBus para validar si hay un jugador en sala espera y confirmando trayendo su datos 
    public void SolicitarJugadorEnSala(JugadorSolicitanteDTO jugador);

    public void setJugadorSolicitante(JugadorSolicitanteDTO jugadorSolicitante);

    public void setPartida(Partida partida);

    //cuando un jugador se una a la sala espera podra votar para futuras solicitudes
    public void suscribirseASalaEspera();

}
