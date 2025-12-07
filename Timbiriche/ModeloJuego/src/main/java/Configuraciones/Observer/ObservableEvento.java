/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Configuraciones.Observer;

import java.util.List;

/**
 *
 * @author Maryr
 */
public interface ObservableEvento {

    void agregarObserver(ObserverEvento o);
    
    void notificarObserver(List<String> usados);
}
