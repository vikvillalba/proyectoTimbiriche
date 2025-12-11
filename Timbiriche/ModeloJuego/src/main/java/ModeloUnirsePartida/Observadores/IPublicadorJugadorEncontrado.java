/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida.Observadores;

import DTO.JugadorConfigDTO;

/**
 * Publicador para notificar cuando se encuentra un host disponible. Implementado por UnirsePartida para notificar al ModeloArranque.
 *
 * @author Jack Murrieta
 */
public interface IPublicadorJugadorEncontrado {

    void agregarNotificadorJugadorEncontrado(INotificadorJugadorEncontrado notificador);

    void notificarJugadorEncontrado(JugadorConfigDTO jugador);
}
