/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import Entidades.Jugador;
import java.util.ArrayList;
import java.util.List;
import org.itson.dto.JugadorDTO;

/**
 *
 * @author pablo
 */
public class MapperJugadores {  
    
    /**
     * Convierte de Jugadores entidad a Jugadores DTO. 
     * @param jugador
     * @return 
     */
    public JugadorDTO toDTO(Jugador jugador){
        JugadorDTO jugadordto = new JugadorDTO();
        jugadordto.setId(jugador.getNombre());
        if (jugador.isTurno()!=true) {
            jugadordto.setTurno(false);
        }else{
            jugadordto.setTurno(true);
        }
        return jugadordto;
    }
    
    /**
     * Convierte de una lista Jugadores entidad a una lista de Jugadores DTO. 
     * @param jugadores
     * @return 
     */
    public List<JugadorDTO> toListaDTO(List<Jugador> jugadores){
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            JugadorDTO jugadorDTO= toDTO(jugador);
            jugadoresDTO.add(jugadorDTO);
        }
        return jugadoresDTO;
    }
    
    public Jugador toEntidad(JugadorDTO jugadordto){
        Jugador jugador = new Jugador();
        jugador.setNombre(jugadordto.getId());
        jugador.setScore(jugadordto.getScore());
        if (jugadordto.isTurno()!=true) {
            jugador.setTurno(false);
        }else{
            jugador.setTurno(true);
        }
        if (jugadordto.isTurno()!=true) {
            jugador.setTurno(false);
        }else{
            jugador.setTurno(true);
        }
        return jugador;
    }
}
