/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Receptor;

import ObserverReceptor.ObservableRecibos;
import ObserverReceptor.ObservadorRecibos;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author erika
 */
public class ColaRecibos implements ObservableRecibos {

    private Queue<String> cola = new LinkedList<>();
    private Gson serializador = new Gson();
    private ObservadorRecibos observadorRecibos;

    @Override
    public void agregarObservador(ObservadorRecibos ob) {
        observadorRecibos = ob;
    }

    @Override
    public void notificar() {
        observadorRecibos.actualizar();
    }

    public void queue(String paquete) {
        cola.add(paquete);
        System.out.println("[ColaRecibos] agregado: " + paquete);
        notificar();

    }

    public PaqueteDTO<?> dequeue() {
        String paquete = cola.poll();
        return deserializar(paquete);
    }

    public PaqueteDTO<?> deserializar(String paquete) {
        return serializador.fromJson(paquete, PaqueteDTO.class);
    }
}
