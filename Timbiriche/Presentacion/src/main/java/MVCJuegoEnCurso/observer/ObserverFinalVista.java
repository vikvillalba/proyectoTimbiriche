/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCJuegoEnCurso.observer;

import java.util.List;
import objetosPresentables.JugadorPresentable;

/**
 *
 * @author Maryr
 */
public interface ObserverFinalVista {

    void finalizarPartida(List<JugadorPresentable> jugadores);

}
