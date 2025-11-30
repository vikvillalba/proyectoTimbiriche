/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCJuegoEnCurso.observer;

/**
 *
 * @author pablo
 */
public interface ObservableMensaje {
    void agregarObservadorMensaje(ObservadorMensaje ob);
    void notificar();
}
