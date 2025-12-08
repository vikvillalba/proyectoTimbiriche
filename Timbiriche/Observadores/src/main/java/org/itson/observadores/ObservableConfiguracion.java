/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.observadores;

import org.itson.dto.ConfiguracionesDTO;

/**
 *Define la interfaz para el sujeto (Observable) de la configuración.
 *Permite que los observadores se registren para recibir notificaciones.
 * @author erika
 */
public interface ObservableConfiguracion {
    /**
     * Agrega un observador a la lista de notificación.
     * @param observadorConfiguracion La instancia del objeto que implementa ObservadorConfiguracion.
     */
    void agregar(ObservadorConfiguracion observadorConfiguracion);
    
    /**
     * Elimina un observador de la lista de notificación.
     * @param observadorConfiguracion
     */
    void eliminar(ObservadorConfiguracion observadorConfiguracion);
    
    /**
     * Notifica a todos los observadores registrados que el estado ha cambiado.
     * @param configuracion El DTO de la configuración a enviar.
     */
    void notificar(ConfiguracionesDTO configuracion);
}
