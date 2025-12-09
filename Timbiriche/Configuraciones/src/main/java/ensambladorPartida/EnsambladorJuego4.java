/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ensambladorPartida;

import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import ensambladorGeneral.EnsambladorPartida;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author pablo
 */
public class EnsambladorJuego4 {
    public static void main(String[] args) {
        Jugador jugador1 = new Jugador(
                "Sol",
                AvatarEnum.TIBURON_MARTILLO,
                ColorEnum.VERDE_PASTEL,
                0,
                false
        );

        Jugador jugador2 = new Jugador(
                "Pablo",
                AvatarEnum.TIBURON_JUMP_BLUE,
                ColorEnum.AZUL_MARINO,
                0,
                false
        );

        Jugador jugador3 = new Jugador(
                "Viki",
                AvatarEnum.TIBURON_BLANCO,
                ColorEnum.MORAS,
                0,
                false
        );

        Jugador jugador4 = new Jugador(
                "Lucia",
                AvatarEnum.TIBURON_STILL_BLUE,
                ColorEnum.MAGENTA,
                0,
                false
        );

        List<Jugador> jugadores = Arrays.asList(jugador1, jugador2,jugador3,jugador4);

        int alto = 10;
        int ancho = 10;

        // Jugador 1 es la sesion de esta ventana
        EnsambladorPartida
                .getInstancia("config_partida4.properties")
                .iniciarPartida(jugadores, alto, ancho, jugador4);
    }
    
}
