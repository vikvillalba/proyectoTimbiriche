/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCConfiguracion.controlador;

/**
 *
 * @author Jack Murrieta
 */
public interface ControladorUnisePartida {

    public void enviarSolicitud(String ip, int puerto); //ip y puerto del jugador a unirse

    //metodo lado del host
    public void aceptarSolicitud(boolean esAceptada);

}
