/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida;

import DTO.JugadorConfigDTO;

/**
 *
 * @author Jack Murrieta
 */
interface IPublicadorHostPartida {
    public void agregarNotificador(INotificadorHostPartida notificador);
    public void notificar(JugadorConfigDTO jugadorHost);
    
}
