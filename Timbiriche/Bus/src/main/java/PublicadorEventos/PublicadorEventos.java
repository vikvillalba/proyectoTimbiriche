/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PublicadorEventos;

import EventBus.EventBus;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class PublicadorEventos {

    private final IEmisor emisor;
    private int puerto;
    private String host;
    private EventBus eventBus;

    public PublicadorEventos(IEmisor emisor) {
        this.emisor = emisor;
    }

    public void publicar(PaqueteDTO paquete) {
        // lógica interna del EventBus
        // salida a la red
        emisor.enviarCambio(paquete);
    }

}
