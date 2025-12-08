/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.observadores;

import org.itson.dto.ConfiguracionesDTO;

/**
 *
 * @author erika
 */// Esta interfaz es implementada por el Observador (ConfiguracionesPartida o un puente)
public interface ObservadorConfiguracion {
    void cambioConfiguracion(ConfiguracionesDTO configuracion);
    
    
}
