package configuración;

import EventBus.EventBus;
import IEmisorBus.IEmisorBus;
import Turnos.ManejadorTurnos;

/**
 *
 * @author pablo
 */
public class EnsambladorTurnos {

//    private EventBus bus;
//    private IEmisorBus emisor;
//    private String host;
//    private int puerto;
//
//    public EnsambladorTurnos(EventBus bus, IEmisorBus emisor, String host, int puerto) {
//        this.bus = bus;
//        this.emisor = emisor;
//        this.host = host;
//        this.puerto = puerto;
//    }
//    
//    public ManejadorTurnos ensamblar(){
//        ManejadorTurnos manejador = new ManejadorTurnos(emisor,host,puerto);
//        
//        bus.registrarServicio("INICIO_PARTIDA", manejador);
//        bus.registrarServicio("ACTUALIZAR_TURNO", manejador);
//        bus.registrarServicio("TURNO_ACTUALIZADO", manejador);
//        
//        return manejador;
//    }
}
