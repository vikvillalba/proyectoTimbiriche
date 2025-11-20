package org.itson.ensamblador;

import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Maryr
 */
public class Ensamblador {

    public static void main(String[] args) {
        Jugador jugador1 = new Jugador(
                "sol",
                AvatarEnum.TIBURON_MARTILLO,
                ColorEnum.VERDE_PASTEL,
                0,
                true
        );

        Jugador jugador2 = new Jugador(
                "pablo",
                AvatarEnum.TIBURON_JUMP_BLUE,
                ColorEnum.AZUL_MARINO,
                0,
                false
        );

        List<Jugador> jugadores = Arrays.asList(jugador1, jugador2);

        int alto = 10;
        int ancho = 10;

        EnsambladorGeneral
                .getInstancia()
                .iniciar(jugadores, alto, ancho);

    }
}
