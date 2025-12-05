/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVCConfiguraciones.modelo;

import org.itson.dto.ConfiguracionesDTO;

/**
 * Interfaz que define las operaciones para la persistencia (guardar/escribir)
 * de la configuración de la aplicación.
 *
 * @author erika
 */
public interface IModeloArranqueExcritura {
    /**
     * Guarda la configuración proporcionada en el medio de almacenamiento 
     *
     * @param cofiguracion El objeto DTO  que contiene
     * los datos de la configuración a guardar.
     */
    void guardarConfiguracion(ConfiguracionesDTO cofiguracion);
}
