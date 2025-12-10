package ensambladorPartida;

import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import configuraciones.EnsambladorJuegoConfig;
import java.util.Arrays;
import java.util.List;

/**
 * Ensamblador para el Jugador 1 - Primera ventana/cliente. Usa
 * config_partida1.properties (puerto.servidor=6000)
 *
 * @author Maryr
 */
public class EnsambladorJuego1 {

    public static void main(String[] args) {
        // Jugadores
        Jugador jugador1 = new Jugador(
                "sol",
                AvatarEnum.TIBURON_MARTILLO,
                ColorEnum.VERDE_PASTEL,
                0,
                false
        );
        Jugador jugador2 = new Jugador(
                "lusia",
                AvatarEnum.TIBURON_STILL_BLUE,
                ColorEnum.MAGENTA,
                0,
                false
        );
        List<Jugador> jugadores = Arrays.asList(jugador1, jugador2);
        
        boolean esHost = true; 


        EnsambladorJuegoConfig.iniciarPartidaCompleta(jugador1, "config_partida1.properties", jugadores, esHost);

        System.out.println("Jugador 'sol' iniciado como HOST y configurando la partida...");
    }
}