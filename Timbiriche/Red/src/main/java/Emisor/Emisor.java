/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Emisor;

import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author erika
 */
public class Emisor implements IEmisor{
    private ColaEnvios cola;
    
    public Emisor(ColaEnvios cola) {
        this.cola = cola;
        System.out.println("cuadruple explosion");
    }

    @Override
    public void enviarCambio(PaqueteDTO paquete) {
        if (paquete == null) {
            throw new IllegalArgumentException("El paquete no puede ser null");
        }
        cola.queue(paquete); 
    }
}
