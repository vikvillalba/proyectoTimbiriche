/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ensambladorPartida;

import ensambladorGeneral.EnsambladorPartida;
import ensambladorGeneral.EnsambladorRegistro;

/**
 *
 * @author Maryr
 */
public class EnsambladorRegistroJugador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EnsambladorRegistro
                .getInstancia("config_partida1.properties")
                .registrarJugador();
    }
    
}
