/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCJuegoEnCurso.observer;

import objetosPresentables.JugadorPresentable;

/**
 *
 * @author pablo
 */
public interface ObservableFinPartida {
    void agregarObservableFinPartida(ObservadorFinPartida ob);
    void notificarFinPartida(JugadorPresentable jugador);
}
