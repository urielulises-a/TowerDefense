package GameLogic;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

public class Bullet extends JComponent{

    private final int DAMAGE = 0, SPEED = 1, RANGE_DAMAGE = 2;
    private final Map<Integer, Float[]> DogBulletCorrelation = new HashMap<Integer,Float[]>(){
        {

            // Relacion de Perro-Municion
            // La llave indica que perro es  y que tipo de municion le corresponden
            // El array de enteros son los valores siendo de la siguiente manera por valores del array (0,1,2,3...)
            // |    0   |            1              |       2       |   
            // |  Daño  |  Velocidad de movimiento  | Rango de daño |
            // Dentro de la clase "Bullet" se instancia el daño y el rango de daño con el tipo de municion (tambien existe tabla de correlacion)

            put(0, new Float[]{} ); // Relacion de "Zeus"
            put(1, new Float[]{} ); // Relacion de "Persefone"
            put(2, new Float[]{} ); // Relacion de "Ares" 
            put(3, new Float[]{} ); // Relacion de "Atenea"
            put(4, new Float[]{} ); // Relacion de "Hades"
            put(5, new Float[]{} ); // Relacion de "Hera"
            put(6, new Float[]{} ); // Relacion de "Hermes"
            put(7, new Float[]{} ); // Relacion de "Cerbero"
            put(8, new Float[]{} ); // Relacion de "Perseo"
            put(9, new Float[]{} ); // Relacion de "Triton"
            put(10,new Float[]{} ); // Relacion de "Hebe"
            put(11,new Float[]{} ); // Relacion de "Cronos"
            put(12,new Float[]{} ); // Relacion de "Teseo"
            put(13,new Float[]{} ); // Relacion de "Demeter"
            
        }};
    
    private Point position;
    private Cats target;
    private float damage,speed, rangeofDamage;
    private boolean active;
    
    public Bullet(Point position, int typeOfBullet) {
        this.position       = position;

        this.damage         = DogBulletCorrelation.get(typeOfBullet)[DAMAGE];
        this.speed          = DogBulletCorrelation.get(typeOfBullet)[SPEED];
        this.rangeofDamage  = DogBulletCorrelation.get(typeOfBullet)[RANGE_DAMAGE];

        this.target = null;

        this.active = true;
    }
    
    public void update() {
        if (active) {
            if (target.isDead()) {
                active = false;         // Si el objetivo esta muerto se deshabilida la municion
            } else {

                double dx = target.getX() - position.getX();
                double dy = target.getY() - position.getY();
                double distance = Math.sqrt(dx*dx + dy*dy);     // Se obtiene la distancia a la que esta el objetivo de la municion
                

                if (distance <= speed) {        // Si la distancia es menor a la velocidad a la que se mueve la municion (es decir , lo alcanza en un tiempo T+1) 
                    for (Cats target : Gameplay.catsInMap) {    // Busca en todos los gatos dentro del mapa
                        if(target.getPosition().distance(position.x, position.y) <= rangeofDamage){ // Si un gato esta cerca del rango de daño se le hace dañp
                            target.damage((int) damage);
                        }
                    }
                    active = false;     // Al ya haber sido activado ahora se desactia el 
                } else {
                    double angle = Math.atan2(dy, dx);          // Si la municion no alcanza el objetivo en un tiempo T+1
                    int vx = (int) (Math.cos(angle) * speed);
                    int vy = (int) (Math.sin(angle) * speed);

                    position.translate(vx, vy);
                }
            }
        }
    }

    
    public boolean isActive() {
        return active;
    }
    
    public Point getPosition() {
        return position;
    }

    public boolean hasReachedTarget() {
        return position.equals(target.getPosition());
    }

    public Cats getTarget() {
        return target;
    }

    public float getDamage(){
        return this.damage;
    }
}
