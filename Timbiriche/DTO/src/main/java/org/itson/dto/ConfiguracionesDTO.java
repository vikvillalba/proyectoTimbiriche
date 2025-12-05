/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dto;

/**
 *
 * @author erika
 */
public class ConfiguracionesDTO {
    private int numJugadores;
    private String tamTablero;

    public ConfiguracionesDTO() {
    }

    public ConfiguracionesDTO(int numJugadores, String tamTablero) {
        this.numJugadores = numJugadores;
        this.tamTablero = tamTablero;
    }

    public int getNumJugadores() {
        return numJugadores;
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }

    public String getTamTablero() {
        return tamTablero;
    }

    public void setTamTablero(String tamTablero) {
        this.tamTablero = tamTablero;
    }
    
    
}
