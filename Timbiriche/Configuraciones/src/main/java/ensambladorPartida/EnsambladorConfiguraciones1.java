/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ensambladorPartida;

import configuraciones.EnsambladorJuegoConfig;
import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Jugador;

/**
 *
 * @author erika
 */
public class EnsambladorConfiguraciones1 {



    public static void main(String[] args) {
        

        Jugador jugador1 = new Jugador(
            "sol",
            AvatarEnum.TIBURON_MARTILLO,
            ColorEnum.VERDE_PASTEL,
            0,
            false // Aún no tiene el turno
        );

       
        EnsambladorJuegoConfig.setJugadorSesion(jugador1);

        EnsambladorJuegoConfig.main(args);
        

    }

    
    
}
