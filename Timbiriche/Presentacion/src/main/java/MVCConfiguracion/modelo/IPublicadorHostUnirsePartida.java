/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCConfiguracion.modelo;

import objetosPresentables.JugadorConfig;

/**
 *
 * @author Jack Murrieta
 */
interface IPublicadorHostUnirsePartida {

    public void agregarNotificadorHostUnirsePartida(INotificadorHostUnirsePartida notificador);

    public void notificar(JugadorConfig jugadorHost);

}
