package ensambladorPartida;

import ensambladorGeneral.EnsambladorPartida;
import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import configuraciones.EnsambladorJuegoConfig;
import java.util.Arrays;
import java.util.List;

/**
 * Ensamblador para el Jugador 2 - Segunda ventana/cliente. Usa
 * config_partida2.properties (puerto.servidor=6002)
 *
 * @author Maryr
 */
public class EnsambladorJuego2 {

    public static void main(String[] args) {
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

        EnsambladorJuegoConfig.iniciarPartidaCompleta(jugador2, "config_partida2.properties", jugadores);

    }
}
