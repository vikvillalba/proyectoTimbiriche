/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Emisor;

import ObserverEmisor.ObservableEnvios;
import ObserverEmisor.ObservadorEnvios;
import com.google.gson.Gson;
import java.util.ArrayDeque;
import java.util.Queue;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author erika
 */
public class ColaEnvios implements ObservableEnvios {

    private Queue<PaqueteDTO> cola = new ArrayDeque<>();
    private Gson serializador = new Gson();
    private ObservadorEnvios observador;

    @Override
    public void agregarObservador(ObservadorEnvios ob) {
        observador = ob;
    }

    @Override
    public void notificar() {
        observador.actualizar();
    }

    public void queue(PaqueteDTO paquete) {
        cola.offer(paquete);
        notificar();
    }

    //?????
    public String dequeue() {
        return serializar(cola.poll());
    }

    public String serializar(PaqueteDTO paquete) {
        String paqueteJson = serializador.toJson(paquete);
        return paqueteJson;
    }

   
}
