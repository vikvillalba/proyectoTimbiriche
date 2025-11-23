package ensambladorPartida;

import ensambladorGeneral.EnsambladorGeneral;
import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import java.util.Arrays;
import java.util.List;

/**
 * Ensamblador para el Jugador 2 - Segunda ventana/cliente.
 * Usa config_partida2.properties (puerto.servidor=6002)
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
                "pablo",
                AvatarEnum.TIBURON_JUMP_BLUE,
                ColorEnum.AZUL_MARINO,
                0,
                false
        );
//
//        Jugador jugador3 = new Jugador(
//                "biki",
//                AvatarEnum.TIBURON_BLANCO,
//                ColorEnum.MORAS,
//                0,
//                false
//        );
//
//        Jugador jugador4 = new Jugador(
//                "lusia",
//                AvatarEnum.TIBURON_STILL_BLUE,
//                ColorEnum.MAGENTA,
//                0,
//                false
//        );

        List<Jugador> jugadores = Arrays.asList(jugador1, jugador2);

        int alto = 10;
        int ancho = 10;

        // Jugador 2 es la sesion de esta ventana
        EnsambladorGeneral
                .getInstancia("config_partida2.properties")
                .iniciarPartida(jugadores, alto, ancho, jugador2);
    }
}
