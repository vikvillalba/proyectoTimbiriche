package org.itson.presentacion;

import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import java.util.Arrays;
import java.util.List;
import partidaFactory.PartidaFactory;

/**
 *
 * @author victoria
 */
public class Presentacion {

    public static void main(String[] args) {

        Jugador jugador1 = new Jugador("sol", AvatarEnum.TIBURON_MARTILLO, ColorEnum.VERDE_PASTEL, 0, false); // cambiar cuando se arreglen los constructores
        Jugador jugador2 = new Jugador("pablo", AvatarEnum.TIBURON_JUMP_BLUE, ColorEnum.AZUL_MARINO, 0, false);
        List<Jugador> jugadores = Arrays.asList(jugador1, jugador2);
        int alto = 10;
        int ancho = 10;
        PartidaFactory.iniciarPartida(jugadores, alto, ancho);
    }
}
