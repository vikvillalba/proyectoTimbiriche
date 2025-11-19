/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ColaRecibosBus;

import ObserverReceptor.ObservableRecibos;
import ObserverReceptor.ObservadorRecibos;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jck Murrieta
 */
public class ColaRecibosBus implements ObservableRecibos {

    private Queue<String> cola = new LinkedList<>();
    private Gson serializador = new Gson();
    private ObservableRecibos observadorRecibos;
    private List<ObservadorRecibos> observadores = new ArrayList<>();

    @Override
    public void agregarObservador(ObservadorRecibos ob) {
        observadores.add(ob);
    }

    @Override
    public void notificar() {
        for (ObservadorRecibos ob : observadores) {
            ob.actualizar();
        }
    }

    public void queue(String paquete) {
        cola.add(paquete);
        notificar();
    }

    public PaqueteDTO<?> dequeue() {
        String paquete = cola.poll();
        return deserializarPaquete(paquete);
    }

    public PaqueteDTO<?> deserializarPaquete(String paquete) {
        PaqueteDTO paqueteTemp = serializador.fromJson(paquete, PaqueteDTO.class);

        try {
            String tipoContenido = paqueteTemp.getTipoContenido();
            Class<?> clase = Class.forName(tipoContenido);

            Object contenidoReal = serializador.fromJson(
                    serializador.toJson(paqueteTemp.getContenido()),
                    clase
        instanceof );
            PaqueteDTO<?> paqueteFinal = new PaqueteDTO<>(
                    contenidoReal,
                    paqueteTemp.getTipoEvento()
            );

            paqueteFinal.setTipoContenido(tipoContenido);

            return paqueteFinal;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
