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
public class EventoWrapper {

    public PaqueteDTO paquete;
    public String host;
    public int puerto;

    public EventoWrapper(PaqueteDTO paquete, String host, int puerto) {
        this.paquete = paquete;
        this.host = host;
        this.puerto = puerto;
    }
}
