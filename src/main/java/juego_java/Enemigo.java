package juego_java;

public class Enemigo {
    // Inicializar variables x e y
    int x;
    int y;

    //Constructores
    public Enemigo(int x, int y){
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
    public void moveEnemy(int dx, int dy){
        this.x += dx;
        this.y += dy;
    }
}
