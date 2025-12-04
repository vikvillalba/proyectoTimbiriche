package ensambladorPartida;

import ensambladorGeneral.EnsambladorPartida;
import org.itson.dto.JugadorDTO;

/**
 *
 * @author victoria
 */
public class EnsambladorJuego3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JugadorDTO jugador = new JugadorDTO();
        jugador.setListo(false);
        jugador.setId("sol");
        jugador.setAvatar("TIBURON_MARTILLO");
        jugador.setColor("MORAS");

        EnsambladorPartida.getInstancia("config_partida3.properties").configurarPartida(jugador);
    }

}
