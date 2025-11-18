/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IEmisorBus;

import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class EmisorBus implements IEmisorBus {

    private ColaEnviosBus cola;

    public EmisorBus(ColaEnviosBus cola) {
        this.cola = cola;
    }

    @Override
    public void enviarCambio(PaqueteDTO paquete, String host, int puertoServicio) {

        if (paquete == null) {
            throw new IllegalArgumentException("El paquete no puede ser null");
        }
        cola.queue(paquete,host,puertoServicio);
    }

}
