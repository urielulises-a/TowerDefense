package GameLogic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Dogs extends JComponent {

    private final int RANGE = 0, ATAK_SPEED = 1, COST_PER_USE = 2, TYPE_BULLET = 3;

    public static final Map<Integer, Integer[]> DogSkillCorrelation = new HashMap<Integer, Integer[]>() {
        {

            // Relacion de Perro-Habilidad
            // La llave indica que perro es y que habilidades le corresponden
            // El array de enteros son los valores siendo de la siguiente manera por valores
            // del array (0,1,2,3...)
            // |   0   |           1           |         2         |         3         |
            // | Rango |  velocidad de ataque  |  Costo por Usarse |  Tipo de municion |
            // Dentro de la clase "Bullet" se instancia el daño y el rango de daño con el
            // tipo de municion (tambien existe tabla de correlacion)

            put(0 , new Integer[] {0,0,0,0}); // Relacion de "Zeus"
            put(1 , new Integer[] {0,0,0,0}); // Relacion de "Persefone"
            put(2 , new Integer[] {0,0,0,0}); // Relacion de "Ares"
            put(3 , new Integer[] {0,0,0,0}); // Relacion de "Atenea"
            put(4 , new Integer[] {0,0,0,0}); // Relacion de "Hades"
            put(5 , new Integer[] {0,0,0,0}); // Relacion de "Hera"
            put(6 , new Integer[] {0,0,0,0}); // Relacion de "Hermes"
            put(7 , new Integer[] {0,0,0,0}); // Relacion de "Cerbero"
            put(8 , new Integer[] {0,0,0,0}); // Relacion de "Perseo"
            put(9 , new Integer[] {0,0,0,0}); // Relacion de "Triton"
            put(10, new Integer[] {0,0,0,0}); // Relacion de "Hebe"
            put(11, new Integer[] {0,0,0,0}); // Relacion de "Cronos"
            put(12, new Integer[] {0,0,0,0}); // Relacion de "Teseo"
            put(13, new Integer[] {0,0,0,0}); // Relacion de "Demeter"

        }
    };

    private int x, y, range, attackSpeed, typeOfDog,costPerUse;
    private Timer attackCooldown;
    private int typeBullet;
    private Cats target;
    private Image dogImage;

    public Dogs(Point position, int dogOption) {

        this.dogImage = new ImageIcon("java/src/main/resources/beware.jpg").getImage();

        this.x = (int) position.getX();
        this.y = (int) position.getY();

        this.typeOfDog = dogOption;

        // El rango de ataque se considera como el radio del circulo para
        this.range = DogSkillCorrelation.get(typeOfDog)[RANGE];

        // La velocidad de ataque es el tiempo en milisegundos (Cadencia de disparo) entre ataques.
        this.attackSpeed = DogSkillCorrelation.get(typeOfDog)[ATAK_SPEED];
        
        // El costo necesario para usarse.
        this.costPerUse = DogSkillCorrelation.get(typeOfDog)[COST_PER_USE];

        // El tipo de municion que maneja el perro.
        this.typeBullet = DogSkillCorrelation.get(typeOfDog)[TYPE_BULLET];

        this.attackCooldown = new Timer(attackSpeed, null);
        this.attackCooldown.setRepeats(true);

        this.target = null;

        Gameplay.dogsInMap.add(this);   // Cada que se instancia un nuevo perro se agrega al array de los perros dentro del mapa
        setBounds(x, y, dogImage.getWidth(null), dogImage.getHeight(null));
        setEnabled(true);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.drawImage(dogImage, x, y, null);
    }
    
    public void update() {
        if (target == null) {
            attackCooldown.stop();
            findTarget();
        } else {
            if (isInRange(target)) {
                if (attackCooldown.getDelay() <= 0) {
                    attack();
                    attackCooldown.restart();
                }
            } else {
                target = null;
            }
        }
    }

    private void findTarget() {
        Cats closestEnemy = null;
        double closestDistance = Double.POSITIVE_INFINITY;
        for (Cats enemy : Gameplay.catsInMap) {
            double distance = getDistance(enemy.getPosition(), getPosition());
            if (distance < closestDistance && distance < range) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }
        target = closestEnemy;
        attackCooldown.start();
    }

    private boolean isInRange(Cats enemy) {
        double distance = getDistance(enemy.getPosition(), getPosition());
        return distance <= range;
    }

    private void attack() {
        Bullet bullet = new Bullet(this.getPosition(), typeBullet);
        super.add(bullet);
    }

    private double getDistance(Point a, Point b) {
        double dx = a.getX() - b.getX(); // Diferencia en X
        double dy = a.getY() - b.getY(); // Diferencia en Y
        return Math.sqrt(dx * dx + dy * dy); // Magnitud del vector de la distancia
    }

    private Point getPosition() {
        return new Point(x, y);
    }

    public int getCostPerUse(){
        return costPerUse;
    }

}
