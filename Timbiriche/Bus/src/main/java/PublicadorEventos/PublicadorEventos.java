/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PublicadorEventos;

import IEmisorBus.IEmisorBus;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class PublicadorEventos {

    private final IEmisorBus emisor;

    public PublicadorEventos(IEmisorBus emisor) {
        this.emisor = emisor;
    }

    public void publicar(PaqueteDTO paquete,String host, int puerto) {
        // lógica interna del EventBus
        // salida a la red
        emisor.enviarCambio(paquete, host, puerto);
    }

}
