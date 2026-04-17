package juego_java;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Mapa {
    // ------> Inicializar
    // Array mapa
    private int[][] map = new int[30][30];

    // Posiciones iniciales jugador
    public int initialX = START;
    public int initialY = START;

    // TODO coleción de enemigos
    public ArrayList<Enemigo> enemigos = new ArrayList<>();

    // Final del juego
    public boolean finalJuego = false;

    // Inicializar números
    public static final int FLOOR = 0;
    public static final int WALL = 1;
    public static final int START = 2;
    public static final int FINAL = 3;
    public static final int ENEMY = 4;

    // ------> Constructores
    // Lector de Niveles
    public Mapa() {
        lectorNiveles(1);
    }

    // ------> Métodos
    // Dibujar el mapa
    public void dibujarMapa(Jugador player) {
        // Recorrido del mapa
        for (int i = 0; i < map.length; i++) { // i = eje y
            for (int j = 0; j < this.map[i].length; j++) { // j = eje x

                // Declara cada casilla
                int box = this.map[i][j];

                // Pinta mapa y jugador
                // Jugador y enemigo con prioridad sobre los demás //TODO
                // Asumimos que no hay fantasma
                boolean hayFantasma = false;

                // Comprobamos uno a uno si está o no
                for (Enemigo enemigo : enemigos) {
                    if (i == enemigo.getY() && j == enemigo.getX()) {
                        System.out.print("GG");
                        hayFantasma = true;
                    }
                }

                if (hayFantasma == true) {

                } else if (i == player.getY() && j == player.getX()) {
                    System.out.print("PP");
                } else if (box == WALL) {
                    System.out.print("##");
                } else if (box == FLOOR) {
                    System.out.print("  ");
                } else if (box == START) {
                    System.out.print("SS");
                } else if (box == FINAL) {
                    System.out.print("FF");
                }
            }
            // Cambia de línea después de pintar una capa
            System.out.println();
        }
    }

    // Detectar muros
    public boolean detectaMuro(int x, int y) {
        if (this.map[y][x] == WALL) {
            return true;
        } else {
            return false;
        }
    }

    // Detectar metas
    /**
     * 
     * @param x = son las columnas
     * @param y = son las filas
     * @return
     */
    public boolean detectarMeta(int x, int y) {
        if (this.map[y][x] == FINAL) {
            return true;
        } else {
            return false;
        }
    }

    // Casilla inicial jugador
    public void posicionInicial(int initalX, int initialY) {
        this.initialX = initalX;
        this.initialY = initialY;
    }


    // Lector de niveles // TODO apuntes
    public void lectorNiveles(int numeroNivel) {
        try {
            this.enemigos.clear();
            File archivo = new File("juego_java/niveles/lvl" + numeroNivel + ".txt");
            Scanner sc = new Scanner(archivo);

            // Contador de filas
            int fila = 0;

            // Inicializar mapa
            this.map = new int[30][30];

            while (sc.hasNextLine() == true) {
                String linea = sc.nextLine();

                // Contador de columnas
                int col = 0;

                // Lee los caracteres de 2 en 2, de cada línea
                for (int i = 0; i < linea.length(); i += 2) {

                    // substring(inicio, fin) coge desde la i hasta i+2
                    String bloque = linea.substring(i, i + 2);

                    // Conversión
                    switch (bloque) {
                        case "##":
                            this.map[fila][col] = WALL;
                            break;
                        case "  ":
                            this.map[fila][col] = FLOOR;
                            break;
                        case "SS":
                            this.map[fila][col] = START;
                            posicionInicial(col, fila);
                            break;
                        case "FF":
                            this.map[fila][col] = FINAL;
                            break;
                        case "GG":
                            // Creaar enemigo y darle los valores de x e y // TODO
                            enemigos.add(new Enemigo(col, fila));
                            break;

                        default:
                            break;
                    }
                    // Cuenta columnas
                    col++;
                }
                // Cuenta filas
                fila++;

            }
        } catch (Exception e) {
            this.finalJuego = true;
            System.err.println("Error: " + e.getMessage()); // Dice en que falló
            e.printStackTrace(); // Dice donde pasó
        }
    }

    // -----> Enemigos
    public boolean moverEnemigos(Jugador player) {
        // Recorremos la lista de enemigos
        for (Enemigo enemigo : this.enemigos) {

            // Inicializar movimiento del enemigo
            int dyEnemigo = 0;
            int dxEnemigo = 0;
            
            // Calcular movimiento random
            int enemyMove = (int) (Math.random() * 4);

            switch (enemyMove) {
                case 0: // W
                    dyEnemigo = -1;
                    break; 
                case 1: // S
                    dyEnemigo = 1;
                    break; 
                case 2: // A
                    dxEnemigo = -1;
                    break; 
                case 3: // D
                    dxEnemigo = 1;
                    break; 
            }

            // Mover si no hay muro
            if (dxEnemigo != 0 || dyEnemigo != 0) {
                int nextX = enemigo.getX() + dxEnemigo;
                int nextY = enemigo.getY() + dyEnemigo;

                // Usamos this.detectaMuro. (Y, X) según tu método actual
                if (!this.detectaMuro(nextX, nextY)) {
                    enemigo.moveEnemy(dxEnemigo, dyEnemigo);
                }
            }

            // Si el jugador y el enemigo están en la misma casilla, el jugador pierde
            if (player.getX() == enemigo.getX() && player.getY() == enemigo.getY()) {
                return true; // Devolvemos true, porque muere
            }
        }
        return false; // Devolvemos false si no muere, sigue vivo
    }
} 
