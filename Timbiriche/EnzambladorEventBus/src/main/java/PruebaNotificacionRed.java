
import EnzambladorEventBus.EnzambladorEventBus;
import org.itson.dto.PaqueteDTO;

/**
 * Prueba de notificación de servicios por red. Cada servicio externo tiene su propio servidor TCP que recibe notificaciones.
 */
public class PruebaNotificacionRed {

    public static void main(String[] args) throws Exception {
        EnzambladorEventBus enzambladorBus = EnzambladorEventBus.getInstancia();
        //ip de el mismo
        enzambladorBus.configurar("localhost", 8001);
        enzambladorBus.ensamblar();

        //realizar que notifique
//       // Hilo que espera 10 segundos antes de enviar el evento
//        Thread hiloEnvio = new Thread(() -> {
//            try {
//                Thread.sleep(5000); // 10 segundos
//
//                PaqueteDTO paqueteEvento = new PaqueteDTO(
//                        "Turno cambiado desde prueba",
//                        "CAMBIO_TURNO",
//                        String.class.getName()
//                );
//
//                System.out.println("[HILO] Enviando evento CAMBIO_TURNO...");
//
//                enzambladorBus.getPublicadorEventos()
//                        .publicar(paqueteEvento, "localhost", 6000);
//
//                System.out.println("[HILO] Evento enviado!");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
    }
}
