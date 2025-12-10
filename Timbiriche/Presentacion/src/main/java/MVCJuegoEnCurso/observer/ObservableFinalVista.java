/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCJuegoEnCurso.observer;

import Entidades.Jugador;
import java.util.List;

/**
 *
 * @author Maryr
 */
public interface ObservableFinalVista {
    void agregarObserverFinalVista(ObserverFinalVista o);
    void notificarObserverFinalVista(List<Jugador> jugadores);
}
