/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida;

import SolicitudEntity.SolicitudUnirse;

/**
 *
 * @author Jack Murrieta
 */
interface IPublicadorSolicitud {
    public void agregarNotificador(INotificadorSolicitud notificador);
    public void notificar(SolicitudUnirse solicitud);
    
}
