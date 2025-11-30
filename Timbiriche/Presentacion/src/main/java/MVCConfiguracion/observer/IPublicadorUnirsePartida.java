/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCConfiguracion.observer;

/**
 *
 * @author Jack Murrieta
 * publicador que notificara a los frms dependiendo del estado de solicitud y respuesta
 */
public interface IPublicadorUnirsePartida {
    //frms qu mostraran el Jdialog
    public void agregarNotificador(INotificadorUnirsePartida notificador);
    //puede ir algo meybe???
    public void notificar();
    
}
