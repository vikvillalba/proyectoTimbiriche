/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida.Emisor;

import DTO.JugadorSolicitanteDTO;
import SolicitudEntity.SolicitudUnirse;

/**
 *
 * @author Jack Murrieta
 */
public interface IUnirsePartidaEnvio {
    //envia la solicitud por el Iemisor

    public void enviarSolicitudSalaEspera(SolicitudUnirse solicitud);

    //respuesta de un jugador en la sala de espera true o false
    public void enviarVotoSolicitud(SolicitudUnirse solicitud);

    //metodo de enviar una peticion al eventBus para validar si hay un jugador en sala espera y confirmando trayendo su datos 
    public void solicitarJugadorEnSala(JugadorSolicitanteDTO jugador);

    //cuando un jugador se una a la sala espera podra votar para futuras solicitudes
    public void suscribirseASalaEspera();

}
