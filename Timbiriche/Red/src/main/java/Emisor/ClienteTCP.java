package Emisor;

import ObserverEmisor.ObservadorEnvios;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author erika
 */
public class ClienteTCP implements ObservadorEnvios {

    private ColaEnvios cola;
    private int puerto;
    private String host;

    @Override
    public void actualizar() {
        try {
            String paqueteSerializado = cola.dequeue();

            if (paqueteSerializado == null) {
                return;
            }

            JsonObject obj = JsonParser.parseString(paqueteSerializado).getAsJsonObject();

            obj.addProperty("host", this.host);
            obj.addProperty("puertoDestino", this.puerto);

            String paqueteConHost = obj.toString();

            enviarPaquete(paqueteConHost, host, puerto);

        } catch (Exception e) {
            System.err.println("Error en ClienteTCP.actualizar(): " + e.getMessage());
        }
    }

    public ClienteTCP(ColaEnvios cola, int puerto, String host) {
        this.cola = cola;
        this.puerto = puerto;
        this.host = host;

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
