/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida.Observadores;

import DTO.JugadorConfigDTO;

/**
 *
 * @author Jack Murrieta
 * actualiza la notificacion que Modelo Unirse Partida mando
 */
public interface INotificadorJugadorEncontrado {
    public void actualizarJugadorEncontrado(JugadorConfigDTO jugador);
    
}
