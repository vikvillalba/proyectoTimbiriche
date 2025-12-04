package ensambladorPartida;

import ensambladorGeneral.EnsambladorPartida;
import org.itson.dto.JugadorDTO;

/**
 *
 * @author victoria
 */
public class EnsambladorJuego4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JugadorDTO jugador = new JugadorDTO();
        jugador.setListo(false);
        jugador.setId("anyi");
        jugador.setAvatar("TIBURON_BLANCO");
        jugador.setColor("ROSA_PASTEL");

        EnsambladorPartida.getInstancia("config_partida4.properties").configurarPartida(jugador);
    }
    
}
