/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCConfiguracion.controlador;

import SolicitudEntity.SolicitudUnirse;

/**
 *
 * @author Jack Murrieta
 */
public interface ControladorUnisePartida {

    public void enviarSolicitud(String ip, int puerto); //ip y puerto del jugador a unirse

    public void enviarSolicitudExistente(SolicitudUnirse solicitud);
    //metodo lado del host
    public void aceptarSolicitud(boolean esAceptada);
    
   public void buscarHostPartida(String ip, int puerto);

}
