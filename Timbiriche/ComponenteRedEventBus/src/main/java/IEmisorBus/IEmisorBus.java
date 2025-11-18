/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package IEmisorBus;

import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public interface IEmisorBus {

    public void enviarCambio(PaqueteDTO paquete, String host, int puertoServicio);

}
