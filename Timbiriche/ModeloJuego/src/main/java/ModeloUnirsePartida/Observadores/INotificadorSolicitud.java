/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida.Observadores;

import SolicitudEntity.SolicitudUnirse;

/**
 *
 * @author Jack Murrieta
 * actualiza a vista paa que se muesten sus jdialogs 
 */
public interface INotificadorSolicitud {
    public void actualizar(SolicitudUnirse solicitud);
    
}
