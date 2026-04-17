package juego_java;

public class Jugador {
    // Declarar variables
    private int x;
    private int y;

    // Constructor
    public Jugador(int x, int y){
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    // Métodos
    public void move(int dx, int dy){
        this.x += dx;
        this.y += dy;
    }
}
