/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer;

import Entidades.Jugador;
import java.util.List;

/**
 *
 * @author Maryr
 */
public interface ObservableFinal {
    void agregarObserverFinal(ObserverFinal o);
    
    void notificarObserverFinal(List<Jugador> jugadores);
}
