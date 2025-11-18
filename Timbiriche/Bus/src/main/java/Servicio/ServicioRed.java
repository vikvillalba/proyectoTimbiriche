/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;

import InterfazServicio.IServicio;

/**
 *
 * @author Jack Murrieta
 */
public class ServicioRed implements IServicio {

    public int puerto;
    public String host;

    public ServicioRed(int puerto, String host) {
        this.puerto = puerto;
        this.host = host;
    }

    @Override
    public int getPuerto() {
        return puerto;
    }

    @Override
    public String getHost() {
        return host;
    }

}
