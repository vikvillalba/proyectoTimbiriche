/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IEmisorBus;

import ObserverEmisor.ObservadorEnvios;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Jack Murrieta
 */
public class ClienteTCPBus implements ObservadorEnvios {

    private ColaEnviosBus cola;

    public ClienteTCPBus(ColaEnviosBus cola) {
        this.cola = cola;
    }

    @Override
    public void actualizar() {
        try {
            EventoWrapper wrapper = cola.dequeue();

            if (wrapper != null) {
                String json = cola.serializar(wrapper.paquete);
                enviarPaquete(json, wrapper.host, wrapper.puerto);
            }

        } catch (Exception e) {
            System.err.println("Error en ClienteTCPBus.actualizar(): " + e.getMessage());
        }
    }

    public void enviarPaquete(String paquete, String host, int puerto) {
        try (Socket socket = new Socket(host, puerto); PrintWriter out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()), true)) {

            out.println(paquete);

        } catch (IOException e) {
            System.err.println("Error enviando paquete por TCP: " + e.getMessage());
        }
    }
}
