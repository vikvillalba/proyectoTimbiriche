/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Receptor;

import ObserverReceptor.ObservadorRecibos;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class ReceptorBus implements ObservadorRecibos {

    private ColaRecibos cola;
    private IReceptor receptor;

    public ReceptorBus(ColaRecibos cola, IReceptor receptor) {
        this.cola = cola;
        this.receptor = receptor;
    }

    @Override
    public void actualizar() {
        PaqueteDTO paquete = cola.dequeue();
        System.out.println("[ReceptorBus] despachando al EventBus: " + paquete.getTipoEvento());
        if (paquete == null) {
            System.out.println("No hay paquetes en cola al recibir la notificación.");
            return;
        }

        if (receptor != null) {
            receptor.recibirCambio(paquete);
        }
    }
}
