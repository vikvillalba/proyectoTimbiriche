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
    public JugadorDTO toDTO(Jugador jugador){
        JugadorDTO jugadordto = new JugadorDTO();
        jugadordto.setId(jugador.getNombre());
        jugadordto.setTurno(jugador.isTurno());
        return jugadordto;
    }
    
    public Jugador toEntidad(JugadorDTO jugadordto){
        Jugador jugador = new Jugador();
        jugador.setNombre(jugadordto.getId());
        jugador.setTurno(jugadordto.isTurno());
        return jugador;
    }
    
    public List<JugadorDTO> toListaDTO(List<Jugador> jugadores){
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            JugadorDTO jugadorDTO= toDTO(jugador);
            jugadoresDTO.add(jugadorDTO);
        }
        return jugadoresDTO;
    }
}
