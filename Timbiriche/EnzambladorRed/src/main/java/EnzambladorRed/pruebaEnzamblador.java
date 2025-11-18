/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package EnzambladorRed;

import DTO.PaqueteSuscripcionDTO;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class pruebaEnzamblador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        // === Servidor A en puerto 5000
        EnzambladorRed ensambladorA = EnzambladorRed.getInstancia()
                .configurar(
                        "localhost", // host destino localhost en eventBus
                        6000, //puerto de enzambladorA
                        8001, //puertoBus sera hardcdeado en cada clienteTCP A unirse
                        new IReceptor() {
                    @Override
                    public void recibirCambio(PaqueteDTO paquete) {
                        System.out.println("[A] Recibí mensaje:");
                        System.out.println("  Tipo: " + paquete.getTipoEvento());
                        System.out.println("  Contenido: " + paquete.getContenido());
                    }
                }
                );

        ensambladorA.ensamblar();

        //Registrar Iservicio cliente
        // asi manda la peticion de registrarsse el clienteTCP
        PaqueteSuscripcionDTO dto = new PaqueteSuscripcionDTO(
                "REGISTRAR", //comando
                "CAMBIO_TURNO", //evento a suscribirse
                "localhost", // Host donde este cliente recibirá eventos
                6000 // Puerto del enzambladorA
        );

        PaqueteDTO paquete = new PaqueteDTO(
                dto,
                "CONTROL_EVENTBUS" // tipo de evento
        );
        
        paquete.setTipoContenido(PaqueteSuscripcionDTO.class.getName());

        ensambladorA.getEmisor().enviarCambio(paquete);
        
        //mostrar el recibir paquete
    }

}
