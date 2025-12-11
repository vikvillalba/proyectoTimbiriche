/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida.Fachada;

import ModeloUnirsePartida.Emisor.IUnirsePartidaEnvio;
import ModeloUnirsePartida.IUnirsePartida;

/**
 * Interfaz fachada que combina las operaciones de negocio y envío
 * para ser usada por la capa de presentación.
 *
 * Esta interfaz permite que la presentación acceda a todas las
 * operaciones necesarias mediante una sola referencia, manteniendo
 * la separación de responsabilidades en las interfaces base.
 *
 * @author Jack Murrieta
 */
public interface IUnirsePartidaFachada extends IUnirsePartida, IUnirsePartidaEnvio {
    // Sirve como punto de acceso único para la capa de presentación
}
