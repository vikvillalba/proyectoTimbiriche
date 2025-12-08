/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.observadores;

import org.itson.dto.ConfiguracionesDTO;

/**
 *
 * @author erika
 */
public interface ObservadorConfiguracionLocal {

    /**
     * Recibe la configuración de la partida de una fuente local (Host).
     *
     * @param configuracion DTO con la configuración de la partida.
     */
    void configuracionRecibida(ConfiguracionesDTO configuracion);

}
