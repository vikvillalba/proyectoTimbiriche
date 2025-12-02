/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida.Observadores;

import SolicitudEntity.SolicitudUnirse;

/**
 * Publicador para notificar cambios en el estado de las solicitudes. Implementado por UnirsePartida para notificar al ModeloArranque.
 *
 * @author Jack Murrieta
 */
public interface IPublicadorSolicitud {

    void agregarNotificadorSolicitud(INotificadorSolicitud notificador);

    void notificarSolicitud(SolicitudUnirse solicitud);
}
