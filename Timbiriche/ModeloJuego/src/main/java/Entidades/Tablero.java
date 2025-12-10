package Entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un tablero de juego.
 *
 * @author victoria
 */
public class Tablero {

    private Punto[][] puntos;
    private int alto;
    private int ancho;
    private ColorEnum color; // enum para el color
    private List<Linea> lineasExistentes;
    private List<Cuadro> cuadrosExistentes; // los cuadros ya se crean pero están vacíos
    private List<Cuadro> cuadrosCompletados; // cuando se llenan

    /**
     * Constructor del tablero.
     *
     * @param alto alto del tablero
     * @param ancho ancho del tablero
     *
     * ambas medidas deben de ser iguales (el tablero es cuadrado) llena el
     * tablero de puntos, configura las adyacencias de cada punto e inicializa
     * cuadros vacíos.
     */
    public Tablero(int alto, int ancho) {
        this.alto = alto;
        this.ancho = ancho;
        this.lineasExistentes = new ArrayList<>();
        this.cuadrosExistentes = new ArrayList<>();
        this.cuadrosCompletados = new ArrayList<>();

        puntos = new Punto[alto][ancho]; // crea la matriz vacía
        llenarTablero();
        configurarAdyacencias();
        configurarCuadrosVacios();

    }

    /**
     * Llena el tablero con puntos.
     */
    private void llenarTablero() {
        //llenar la matriz con puntos
        for (int i = 0; i < alto; i++) { // recorre filas
            for (int j = 0; j < ancho; j++) { // recorre elementos de la fila
                Punto nuevoPunto = new Punto(i, j);
                puntos[i][j] = nuevoPunto; // agregar punto
                System.out.println("punto [" + i + "], [" + j + "] creado");
            }
        }
    }

    /**
     * Configura las adyacencias de cada punto.
     */
    private void configurarAdyacencias() {
        for (int i = 0; i < alto; i++) { // fila
            for (int j = 0; j < ancho; j++) { // elementos de la fila
                Punto punto = puntos[i][j];
                if (i > 0) { // si no son puntos del borde izquierdo
                    punto.setArriba(puntos[i - 1][j]);
                }
                if (i < alto - 1) { //si no son puntos del borde derecho
                    punto.setAbajo(puntos[i + 1][j]);
                }
                if (j > 0) {  // si no son puntos del borde izquierdo
                    punto.setIzquierda(puntos[i][j - 1]);
                }
                if (j < ancho - 1) { //si no son puntos del borde derecho
                    punto.setDerecha(puntos[i][j + 1]);
                }
                // osea q los puntos del borde izquierdo no tienen adyacentes a la izquierda
                // los puntos del borde derecho no tienen adyacentes a la derecha
                // los puntos de las esquinas de la primera fila no tienen adyacentes arriba
                // los puntos de las esquinas de la última fila no tienen adyacentes abajo
            }
        }
    }

    /**
     * Crea cuadros vacíos. el tablero ya conoce los cuadros que se pueden
     * llenar durante el juego.
     */
    private void configurarCuadrosVacios() {
        // crea los cuadros vacíos, pero el tablero ya sabe cuales son los cuadros q se pueden formar 
        for (int i = 0; i < alto - 1; i++) {
            for (int j = 0; j < ancho - 1; j++) { // omite los puntos de los bordes (pero si se agregan como aristas)
                Cuadro cuadro = new Cuadro();
                cuadro.agregarArista(puntos[i][j]);
                cuadro.agregarArista(puntos[i + 1][j]);
                cuadro.agregarArista(puntos[i][j + 1]);
                cuadro.agregarArista(puntos[i + 1][j + 1]);
                cuadrosExistentes.add(cuadro);
            }
        }
    }

    /**
     * Une dos puntos para crear una linea.
     *
     * @param origen punto de inicio
     * @param destino punto de fin
     * @param jugadorActual jugador que conecta los puntos
     * @return llamada al método llenar cuadro, que determina si un cuadro se
     * completa o no.
     */
    public boolean unirPuntos(Punto origen, Punto destino, Jugador jugadorActual) {
        Linea lineaTemp = new Linea(origen, destino, jugadorActual); // línea sabe su dueño
        this.lineasExistentes.add(lineaTemp);
        return llenarCuadro(lineaTemp, jugadorActual);
    }

    /**
     * Verifica si una línea creada puede completar un cuadro.
     */
    private boolean llenarCuadro(Linea linea, Jugador jugador) {
        boolean completado = false;
        for (Cuadro cuadro : this.cuadrosExistentes) {
            List<Punto> aristas = cuadro.getAristas();
            if (aristas.contains(linea.getOrigen()) && aristas.contains(linea.getDestino())) {
                cuadro.agregarVertice(linea);
                if (cuadro.getVertices().size() == 4) {
                    cuadro.setDueno(jugador); // asigna el jugador que completo el cuadro
                    cuadro.setColor(cuadro.getDueno().getColor());
                    cuadrosCompletados.add(cuadro);
                    System.out.println("Cuadro completo, cuadro de: " + cuadro.getDueno().getNombre());
                    completado = true;
                    cuadro.getDueno().sumarScore();
                }
            }
        }
        return completado;
    }

    public Punto getPunto(int x, int y) {
        return puntos[x][y];
    }

    public Punto[][] getPuntos() {
        return puntos;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public ColorEnum getColor() {
        return color;
    }

    public List<Linea> getLineasExistentes() {
        return lineasExistentes;
    }

    public List<Cuadro> getCuadrosExistentes() {
        return cuadrosExistentes;
    }

    public List<Cuadro> getCuadrosCompletados() {
        return cuadrosCompletados;
    }

}
