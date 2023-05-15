package GameLogic;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;



public class Cats {
    private final int HEALTH = 0 , SPEED = 1, REWARD = 2, SKILL = 3;
    private static final Map<Integer, Integer[]> CatSkillCorrelation = new HashMap<Integer, Integer[]>() {
        {

            // Relacion de Gato-Habilidad
            // La llave indica que gato es y que habilidades le corresponden
            // El array de enteros son los valores siendo de la siguiente manera por valores
            // del array (0,1,2,3...)
            // |   0   |             1              |            2              |         3          |
            // | Vida  |  velocidad de movimiento   |   Cantidad de recompensa  |  Tipo de habilidad |
            // Dentro de la clase "Bullet" se instancia el daño y el rango de daño con el
            // tipo de municion (tambien existe tabla de correlacion)

            put(0 , new Integer[] {0,0,0,0}); // Relacion de "Bastet"
            put(1 , new Integer[] {0,0,0,0}); // Relacion de "Anubis"
            put(2 , new Integer[] {0,0,0,0}); // Relacion de "Isis"
            put(3 , new Integer[] {0,0,0,0}); // Relacion de "Horus"
            put(4 , new Integer[] {0,0,0,0}); // Relacion de "Maahes"
            put(5 , new Integer[] {0,0,0,0}); // Relacion de "Mafdet"
            put(6 , new Integer[] {0,0,0,0}); // Relacion de "Mau"
            put(7 , new Integer[] {0,0,0,0}); // Relacion de "Neftis"
            put(8 , new Integer[] {0,0,0,0}); // Relacion de "Ra"
            put(9 , new Integer[] {0,0,0,0}); // Relacion de "Sekhmet"
            put(10, new Integer[] {0,0,0,0}); // Relacion de "Sobek"
            put(11, new Integer[] {0,0,0,0}); // Relacion de "Thoth"
            put(12, new Integer[] {0,0,0,0}); // Relacion de "Wadjet"
            put(13, new Integer[] {0,0,0,0}); // Relacion de "Anput"
            put(14, new Integer[] {0,0,0,0}); // Relacion de "Osiris"

        }
    };
    
    private Point position;
    private int health;
    private int speed;
    private int reward;
    private int currentPathIndex;
    private Path currentPath;
    
    public Cats(Point position, int typeOfCat, Path path) {
        this.position = position;
        this.health = CatSkillCorrelation.get(typeOfCat)[HEALTH];
        this.speed = CatSkillCorrelation.get(typeOfCat)[SPEED];
        this.reward = CatSkillCorrelation.get(typeOfCat)[REWARD];
        this.currentPathIndex = 0;
        this.currentPath = path;
    }
    
    public void update() {
        if (currentPathIndex < currentPath.getLength()) {
            Point nextPosition = currentPath.getPosition(currentPathIndex);
            double distance = position.distance(nextPosition);
            if (distance <= speed) {
                position.setLocation(nextPosition);
                currentPathIndex++;
            } else {
                double dx = nextPosition.getX() - position.getX();
                double dy = nextPosition.getY() - position.getY();
                double angle = Math.atan2(dy, dx);
                int vx = (int) (Math.cos(angle) * speed);
                int vy = (int) (Math.sin(angle) * speed);
                position.translate(vx, vy);
            }
        }
    }
    
    public boolean isDead() {
        return health <= 0;
    }
    
    public void damage(int amount) {
        health -= amount;
    }
    
    public void setPosition(Point position) {
        this.position = position;
    }
    
    public Point getPosition() {
        return position;
    }
    
    public int getReward() {
        return reward;
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }
}
