/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.modelojuego;

import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;
import Entidades.ManejadorTurnos;
import Entidades.Partida;
import Entidades.Punto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class manejadorTurnosPrueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Jugador j1 = new Jugador("j1", AvatarEnum.TIBURON_BLANCO, ColorEnum.MORAS, 0, false);
        Jugador j2 = new Jugador("j2", AvatarEnum.TIBURON_BALLENA, ColorEnum.NARANJA__PASTEL, 0, false);
        Jugador j3 = new Jugador("j3", AvatarEnum.TIBURON_MARTILLO, ColorEnum.ROSA_PASTEL, 0, false);
        Jugador j4 = new Jugador("j4", AvatarEnum.TIBURON_SMILE_BLUE, ColorEnum.MAGENTA, 0, false);

        List<Jugador> jugadores = new ArrayList();
        jugadores.add(j2);
        jugadores.add(j1);

        jugadores.add(j3);

        jugadores.add(j4);

        System.out.println(jugadores.toString());
        ManejadorTurnos manejador = new ManejadorTurnos(jugadores);

        Partida partida = new Partida(jugadores, 10, 10);

        //puntos
        System.out.println("=== INICIO DE PARTIDA ===");
        System.out.println("Primer turno: " + partida.getJugadores().get(0).getNombre());

        // --- JUGADA 1 ---
        Punto p1 = partida.getPuntoTablero(0, 0);
        Punto p2 = partida.getPuntoTablero(0, 1);

        System.out.println("\nJugada 1: conectar " + p1 + " con " + p2);
        partida.validarPuntos(p1, p2);
        partida.actualizarTurno();
        System.out.println("Turno después de jugada 1: " + partida.getJugadores().get(1).getNombre());

        // --- JUGADA 2 ---
        Punto p3 = partida.getPuntoTablero(1, 0);
        Punto p4 = partida.getPuntoTablero(1, 1);

        System.out.println("\nJugada 2: conectar " + p3 + " con " + p4);
        partida.validarPuntos(p3, p4);
        partida.actualizarTurno();
        System.out.println("Turno después de jugada 2: " + partida.getJugadores().get(2).getNombre());

        System.out.println("\n=== FIN DE PRUEBA ===");
    }

//        System.out.println(manejador.getTurnos().toString());
//
//        for (int i = 0; i < jugadores.size(); i++) {
//            manejador.actualizarTurno();
//            System.out.println(manejador.getJugadorEnTurno().getNombre());
//        }
//
//    }
}
