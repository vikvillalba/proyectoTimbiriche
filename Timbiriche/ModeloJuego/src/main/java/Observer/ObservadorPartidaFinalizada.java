/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer;

import Entidades.Jugador;
import org.itson.dto.JugadorDTO;

/**
 *
 * @author pablo
 */
public interface ObservadorPartidaFinalizada {
    void finalizarPartida(Jugador jugador);
}
