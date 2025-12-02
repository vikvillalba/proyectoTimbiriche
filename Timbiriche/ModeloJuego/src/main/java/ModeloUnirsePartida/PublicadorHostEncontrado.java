/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida;

import DTO.JugadorConfigDTO;

/**
 * Publicador para notificar cuando se encuentra un host disponible. Implementado por UnirsePartida para notificar al ModeloArranque.
 *
 * @author Jack Murrieta
 */
public interface PublicadorHostEncontrado {

    void agregarNotificadorHostEncontrado(INotificadorHostEncontrado notificador);

    void notificarHostEncontrado(JugadorConfigDTO jugador);
}
