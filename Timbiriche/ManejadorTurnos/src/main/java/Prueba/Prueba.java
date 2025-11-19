/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Prueba;

import EventBus.EventBus;

import PublicadorEventos.PublicadorEventos;
import Turnos.ManejadorTurnos;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author pablo
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String host = "localhost";
//        int puerto = 5000;
//         // 1. Crear EventBus
//        EventBus bus = new EventBus(puerto,host);
//
//        ColaEnviosBus cola = new ColaEnviosBus();
//        
//        IEmisorBus emisor = new EmisorBus(cola); // Si no tienes uno, te dejo ejemplo abajo
//
//        // 3. Crear ensamblador de turnos
//        EnsambladorTurnos ensambladorTurnos = new EnsambladorTurnos(
//                bus, emisor,
//                host,
//                puerto
//        );
//
//        // 4. Construir el MANEJADOR (ya registrado al bus)
//        ManejadorTurnos manejador = ensambladorTurnos.ensamblar();
//
//        // 5. Crear inyector de dependencias
//        InyectorDependencias inyector = new InyectorDependencias();
//
//        // 6. Inyectar el receptor (el manejador)
//        inyector.setReceptor(manejador);
//
//        // 7. Conectar con la red (inicia servidor TCP + registrar receptor)
//        inyector.conectarConRed(host, puerto);
//
//        // 8. PROBAR enviando un evento del bus
//        PublicadorEventos publicador = new PublicadorEventos(emisor);
//        
//        bus.setPublicadorEventos(publicador);
//        
//        PublicadorEventos p = bus.getPublicadorEventos();
//
//        // 10. Ver la recepción
//        System.out.println("Prueba finalizada");
    }
    
}
