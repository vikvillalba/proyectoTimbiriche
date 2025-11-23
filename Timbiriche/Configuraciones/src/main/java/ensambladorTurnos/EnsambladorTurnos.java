package ensambladorTurnos;

import ensambladorGeneral.EnsambladorGeneral;

/**
 *
 * @author victoria
 */
public class EnsambladorTurnos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EnsambladorGeneral.getInstancia("config_turnos.properties").iniciarManejadorTurnos();
    }
    
}
