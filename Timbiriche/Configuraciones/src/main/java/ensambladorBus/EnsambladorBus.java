package ensambladorBus;

import ensambladorGeneral.EnsambladorGeneral;

/**
 *
 * @author victoria
 */
public class EnsambladorBus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EnsambladorGeneral.getInstancia("config_bus.properties").iniciarBus();
    }
    
}
