package GameLogic;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

public class Bullet extends JComponent {

    private final int DAMAGE = 0, SPEED = 1, RANGE_DAMAGE = 2;
    private final Map<Integer, Double[]> DogBulletCorrelation = new HashMap<Integer, Double[]>() {
        {

            // Relacion de Perro-Municion
            // La llave indica que perro es y que tipo de municion le corresponden
            // El array de enteros son los valores siendo de la siguiente manera por valores
            // del array (0,1,2,3...)
            // | 0 | 1 | 2 |
            // | Daño | Velocidad de movimiento | Rango de daño |
            // Dentro de la clase "Bullet" se instancia el daño y el rango de daño con el
            // tipo de municion (tambien existe tabla de correlacion)

            put(0,  new Double[] { 0.5  , 100.0 , 1.0   }); // Relacion de "Zeus"
            put(1,  new Double[] { 50.0 , 5.0   , 100.0 }); // Relacion de "Persefone"
            put(2,  new Double[] { 5.0  , 100.0 , 1.0   }); // Relacion de "Ares"
            put(3,  new Double[] { 20.0 , 10.0  , 100.0 }); // Relacion de "Atenea"
            put(4,  new Double[] { 5.0  , 100.0 , 100.0 }); // Relacion de "Hades"
            put(5,  new Double[] { 15.0 , 10.0  , 1.0   }); // Relacion de "Hera"
            put(6,  new Double[] { 0.5  , 1000.0, 1.0   }); // Relacion de "Hermes"
            put(7,  new Double[] { 60.0 , 15.0  , 200.0 }); // Relacion de "Cerbero"
            put(8,  new Double[] { 80.0 , 1000.0, 1.0   }); // Relacion de "Perseo"
            put(9,  new Double[] { 15.0 , 5.0   , 50.0  }); // Relacion de "Triton"
            put(10, new Double[] { 40.0 , 1000.0, 1.0   }); // Relacion de "Hebe"
            put(11, new Double[] { 1.0  , 1000.0, 1.0   }); // Relacion de "Cronos"
            put(12, new Double[] { 30.0 , 3.0   , 100.0 }); // Relacion de "Teseo"
            put(13, new Double[] { 20.0 , 100.0 , 1.0   }); // Relacion de "Demeter"

        }
    };

    private Point position;
    private Cats target;
    private double damage, speed, rangeofDamage;

    public Bullet(Point position, int typeOfBullet, Cats target) {
        this.position = position;

        this.damage = DogBulletCorrelation.get(typeOfBullet)[DAMAGE];
        this.speed = DogBulletCorrelation.get(typeOfBullet)[SPEED];
        this.rangeofDamage = DogBulletCorrelation.get(typeOfBullet)[RANGE_DAMAGE];

        this.target = target;

        Gameplay.bulletsInMap.add(this);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (target != null) {
            g.setColor(Color.BLACK);
            g.fillRect((int) position.getX(), (int) position.getY(), 20, 20);
        }
    }

    public void update() {
        if (target != null) {
            if (target.isDead()) {
                this.target = null;
            } else {

                double distance = distance(target.getPosition());
                if (distance <= speed) {
                    synchronized(Gameplay.catsInMap){
                    position.x = (int) target.getPosition().getX();
                    position.y = (int) target.getPosition().getY();
                    for (Cats cats : Gameplay.catsInMap) {
                        if(distance(cats.getPosition()) <= rangeofDamage){
                            cats.damage(damage);
                        }
                    }
                    target = null;
                }
                } else {
                    double dx = target.getPosition().getX() - position.x;
                    double dy = target.getPosition().getY() - position.y;
                    double magnitude = Math.sqrt(dx * dx + dy * dy);
                    double directionX = dx / magnitude;
                    double directionY = dy / magnitude;
                    double displacementX = directionX * speed;
                    double displacementY = directionY * speed;

                    position.x += displacementX;
                    position.y += displacementY;
                }
            }
        }
    }

    public boolean isActive() {
        return target != null;
    }

    public Point getPosition() {
        return position;
    }

    // public boolean hasReachedTarget() {
    //     return position.equals(target.getPosition());
    // }

    public Cats getTarget() {
        return target;
    }

    public double getDamage() {
        return this.damage;
    }

    private int distance(Point point) {
        int dx = (int) (point.getX() - position.x);
        int dy = (int) (point.getY() - position.y);
        return (int) Math.sqrt(dx * dx + dy * dy);
    }
}
