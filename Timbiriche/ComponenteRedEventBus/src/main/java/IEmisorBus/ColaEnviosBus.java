/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IEmisorBus;

import ObserverEmisor.ObservableEnvios;
import ObserverEmisor.ObservadorEnvios;
import com.google.gson.Gson;
import java.util.ArrayDeque;
import java.util.Queue;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class ColaEnviosBus implements ObservableEnvios {

    private Queue<EventoWrapper> cola = new ArrayDeque<>();
    private ObservadorEnvios observador;
    private Gson gson = new Gson();

    @Override
    public void agregarObservador(ObservadorEnvios ob) {
        this.observador = ob;
    }

    @Override
    public void notificar() {
        if (observador != null) {
            observador.actualizar();
        }
    }

    public void queue(PaqueteDTO paquete, String host, int puerto) {
        cola.offer(new EventoWrapper(paquete, host, puerto));
        notificar();
    }

    public EventoWrapper dequeue() {
        return cola.poll();
    }

    public String serializar(PaqueteDTO paquete) {
        return gson.toJson(paquete);
    }

}
