/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ensambladorPartida;

import ensambladorGeneral.EnsambladorEsperaFake;

/**
 *
 * @author Maryr
 */
public class EnsambladorEsperaPrueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EnsambladorEsperaFake
                .getInstancia("config_partida2.properties")
                .iniciarEsperaFake();
    }
    
}
