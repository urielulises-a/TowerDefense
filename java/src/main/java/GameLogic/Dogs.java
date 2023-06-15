package GameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Dogs extends JComponent {

    private final static int RANGE = 0, ATAK_SPEED = 1, COST_PER_USE = 2, TYPE_BULLET = 3;

    public static final Map<Integer, Integer[]> DogSkillCorrelation = new HashMap<Integer, Integer[]>() {
        {

            // Relacion de Perro-Habilidad
            // La llave indica que perro es y que habilidades le corresponden
            // El array de enteros son los valores siendo de la siguiente manera por valores
            // del array (0,1,2,3...)
            // | 0 | 1 | 2 | 3
            // | Rango | velocidad de ataque | Costo por Usarse | Tipo de municion
            // Dentro de la clase "Bullet" se instancia el daño y el rango de daño con el
            // tipo de municion (tambien existe tabla de correlacion)

            put(0,  new Integer[] { 200 , 10    , 100   , 0 }); // Relacion de "Zeus"
            put(1,  new Integer[] { 300 , 2000  , 50    , 1 }); // Relacion de "Persefone"
            put(2,  new Integer[] { 200 , 1     , 25    , 2 }); // Relacion de "Ares"
            put(3,  new Integer[] { 200 , 500   , 50    , 3 }); // Relacion de "Atenea"
            put(4,  new Integer[] { 300 , 1     , 150   , 4 }); // Relacion de "Hades"
            put(5,  new Integer[] { 150 , 1000  , 75    , 5 }); // Relacion de "Hera"
            put(6,  new Integer[] { 300 , 1000  , 200   , 6 }); // Relacion de "Hermes"
            put(7,  new Integer[] { 400 , 2000  , 100   , 7 }); // Relacion de "Cerbero"
            put(8,  new Integer[] { 700 , 4000  , 150   , 8 }); // Relacion de "Perseo"
            put(9,  new Integer[] { 200 , 500   , 75    , 9 }); // Relacion de "Triton"
            put(10, new Integer[] { 700 , 3000  , 150   , 10}); // Relacion de "Hebe"
            put(11, new Integer[] { 700 , 500   , 200   , 11}); // Relacion de "Cronos"
            put(12, new Integer[] { 300 , 2000  , 75    , 12}); // Relacion de "Teseo"
            put(13, new Integer[] { 300 , 3000  , 100   , 13}); // Relacion de "Demeter"

        }
    };
    private boolean isSelected;
    private int x, y, range, attackSpeed, typeOfDog, costPerUse;
    private int typeBullet;
    public JPopupMenu dogsOptions;
    private Timer attackCooldown;
    private Cats target;
    private Image dogImage;
    private static String dogImagePath = "java/src/main/resources/CharactersImages/Perros/";

    public Dogs(Point position, int dogOption) {

        this.isSelected = false;
        this.dogImage = new ImageIcon(dogImagePath + dogOption + ".png").getImage().getScaledInstance(90, 90,
                Image.SCALE_DEFAULT);

        this.x = (int) position.getX();
        this.y = (int) position.getY();

        this.typeOfDog = dogOption;

        // El rango de ataque se considera como el radio del circulo para
        this.range = DogSkillCorrelation.get(typeOfDog)[RANGE];

        // La velocidad de ataque es el tiempo en milisegundos (Cadencia de disparo)
        // entre ataques.
        this.attackSpeed = DogSkillCorrelation.get(typeOfDog)[ATAK_SPEED];

        // El costo necesario para usarse.
        this.costPerUse = DogSkillCorrelation.get(typeOfDog)[COST_PER_USE];

        // El tipo de municion que maneja el perro.
        this.typeBullet = DogSkillCorrelation.get(typeOfDog)[TYPE_BULLET];

        this.attackCooldown = new Timer(attackSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (Gameplay.bulletsInMap) {
                    attack();
                }
            }
        });
        this.attackCooldown.setInitialDelay(0);
        this.attackCooldown.setRepeats(true);

        this.dogsOptions = setupMenu();

        this.target = null;

        Gameplay.dogsInMap.add(this); // Cada que se instancia un nuevo perro se agrega al array de los perros dentro
                                      // del mapa

        setBounds(x, y, dogImage.getWidth(null), dogImage.getHeight(null));
        setEnabled(true);
        setVisible(true);
        setName(String.valueOf(dogOption));
    }

    private JPopupMenu setupMenu() {
        JPopupMenu menu = new JPopupMenu("Opciones");
        JMenuItem eliminarOpcion = new JMenuItem("Eliminar perro");
        Dogs currentDog = this;

        // Agregar ActionListener a cada elemento de menú
        eliminarOpcion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized(Gameplay.dogsInMap){
                    Gameplay.dogsInMap.remove(currentDog);
                    currentDog.attackCooldown.stop();
                }
                isSelected = false;
            }
        });

        menu.add(eliminarOpcion);


        return menu;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g);

        if (isSelected) {
            g2d.setColor(new Color(255, 0, 0, 128)); // Amarillo opaco (con transparencia)
            int x = (int) getX();
            int y = (int) getY();
            g2d.fillOval(x - 45, y - 45, 90, 90);
        }

        g.drawImage(dogImage, x - 45, y - 45, null);

    }

    public void update() {

        if (target == null || target.isDead()) {
            attackCooldown.stop();
            findTarget();
        } else {
            if (!isInRange(target)) {
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
        Bullet bullet = new Bullet(this.getPosition(), typeBullet, target);
    }

    private double getDistance(Point a, Point b) {
        double dx = a.getX() - b.getX(); // Diferencia en X
        double dy = a.getY() - b.getY(); // Diferencia en Y
        return Math.sqrt(dx * dx + dy * dy); // Magnitud del vector de la distancia
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public int getCostPerUse() {
        return costPerUse;
    }

    public Cats getTarget() {
        return target;
    }

    public static String getDogImagePath(int typeDog) {
        return dogImagePath + typeDog + ".png";
    }

    public static String getCostOfDog(int typeDog) {
        return String.valueOf(DogSkillCorrelation.get(typeDog)[Dogs.COST_PER_USE]);
    }

    public void setSelected(boolean Selected) {
        this.isSelected = Selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void stopAtack(){
        this.attackCooldown.stop();
    }

    public boolean clickInDog(Point click) {
        return (click.getX() >= getX() - 45 && click.getX() <= getX() + 45
                && click.getY() >= getY() - 45 && click.getY() <= getY() + 45);
    }

}
