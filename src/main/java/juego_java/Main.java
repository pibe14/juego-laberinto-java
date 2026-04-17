package juego_java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- PUZZLE LABERINTO ---");

        // Inicialización de variables
        Scanner sc = new Scanner(System.in);

        // Crear objetos
        Mapa map = new Mapa();
        Jugador player = new Jugador(map.initialX, map.initialY);

        // Controlador de partida y niveles
        boolean active = true;
        // contador de niveles
        int nivelActual = 1; // para el flujo y el cambio de niveles
        int contNivel = 0; // para mostrar en la pantalla

        // ----> FLUJO DE JUEGO
        while (active == true) {

            // Dibujar mapa
            map.dibujarMapa(player);

            // -----> MOVIMIENTO Y COLISIONES
            System.out.println("Para mover utiliza WASD");
            String move = sc.nextLine();

            // Inicializar movimiento (estático por defecto) Jugador
            int dy = 0;
            int dx = 0;

            // Asignar teclas de movimiento para el Jugador
            if (move.equalsIgnoreCase("W")) {
                dy--;
            } else if (move.equalsIgnoreCase("S")) {
                dy++;
            } else if (move.equalsIgnoreCase("A")) {
                dx--;
            } else if (move.equalsIgnoreCase("D")) {
                dx++;
            }

            // Colisiones y movimiento
            // Jugador
            if (dx != 0 || dy != 0) {
                int nextY = player.getY() + dy;
                int nextX = player.getX() + dx;

                if (!map.detectaMuro(nextX, nextY)) {
                    player.move(dx, dy);
                }
            }

            // Inicializa la casilla final en base a la posición del jugador
            boolean end = map.detectarMeta(player.getX(), player.getY());

            // -----> NIVELES Y FINAL DEL JUEGO

            // Limpiar pantalla (//TODO: con IA porque no sé como se hace)
            try {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } catch (Exception e) {
                for (int i = 0; i < 50; i++)
                    System.out.println();
            }

            // Bucle de flujo
            if (end == true) {
                contNivel++;
                nivelActual++;

                // Cargar el nivel actual
                map.lectorNiveles(nivelActual);

                // Acaba el juego al no haber más niveles
                if (map.finalJuego == true) {
                    System.out.println("¡¡ENHORABUENA, TE HAS PASADO EL JUEGO!!");
                    active = false;
                } else {
                    // Reiniciar posición del jugador y del enemigo
                    player = new Jugador(map.initialX, map.initialY);

                    System.out.println("¡NIVEL " + contNivel + " SUPERADO!");
                }
            }

            // Vincular enemigos al main
            boolean muerto = map.moverEnemigos(player);
            if (muerto) {
                System.out.println("¡GAME OVER!");
                map.dibujarMapa(player);
                active = false;
            }

        }

    }

}
