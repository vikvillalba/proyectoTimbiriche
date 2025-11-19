/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicio;



/**
 *
 * @author Jack Murrieta
 */
public class Servicio  {
    
    private String id;
    public int puerto;
    public String host;

    public Servicio(int puerto, String host) {
        this.puerto = puerto;
        this.host = host;
    }

    
    public int getPuerto() {
        return puerto;
    }


    public String getHost() {
        return host;
    }

}
