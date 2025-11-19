/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PublicadorEventos;

import EventBus.EventBus;
import Servicio.Servicio;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class PublicadorEventos implements IReceptor {

    private final IEmisor emisor;
    private int puerto;
    private String host;
    private EventBus eventBus;

    public PublicadorEventos(IEmisor emisor) {
        this.emisor = emisor;
    }

    public void publicar(PaqueteDTO paquete) {
        // lógica interna del EventBus
        // salida a la red
       eventBus.publicarEvento(paquete.getTipoEvento(), (String) paquete.getContenido());
        emisor.enviarCambio(paquete);
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
       eventBus.publicarEvento(paquete.getTipoEvento(), (String) paquete.getContenido());
    }
    
    public void iniciar() {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(puerto)) {
                while (true) {
                    Socket cliente = server.accept();
                    recibirEvento(cliente);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
     public void recibirEvento(Socket cliente) {
        try {
            ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
            PaqueteDTO paquete = (PaqueteDTO) in.readObject();
            recibirCambio(paquete);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     public void registrarDestino(int puerto, String host) {
        Servicio servicio = new Servicio( puerto, host);
        eventBus.agregarEvento("default", servicio); 
        // según diagrama: agregar servicio a un evento
    }

}
