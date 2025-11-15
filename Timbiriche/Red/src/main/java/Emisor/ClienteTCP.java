/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Emisor;

import ObserverEmisor.ObservadorEnvios;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author erika
 */
public class ClienteTCP implements ObservadorEnvios{
    private ColaEnvios cola;
    private int puertoBus;
    private String hostBus;
    
    
    
    @Override
    public void actualizar() {
        try {
            String paqueteSerializado = cola.dequeue();
            
            if (paqueteSerializado != null) {
                enviarPaquete(paqueteSerializado);
            }

        } catch (Exception e) {
            System.err.println("Error en ClienteTCP.actualizar(): " + e.getMessage());
        }
    }
    
    public ClienteTCP(ColaEnvios cola, int puerto, String host) {
        this.cola = cola;
        this.puertoBus = puerto;
        this.hostBus = host;
        
        
    }
    
    public void enviarPaquete(String paquete) {
        try (Socket socket = new Socket(hostBus, puertoBus);
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream()), true)) {

            out.println(paquete);

        } catch (IOException e) {
            System.err.println("Error enviando paquete por TCP: " + e.getMessage());
        }
    }
}
