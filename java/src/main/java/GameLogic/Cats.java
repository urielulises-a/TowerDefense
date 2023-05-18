package GameLogic;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComponent;



public class Cats extends JComponent{
    private final int HEALTH = 0 , SPEED = 1, REWARD = 2, SKILL = 3;
    private static final Map<Integer, Double[]> CatSkillCorrelation = new HashMap<Integer,Double[]>() {
        {

            // Relacion de Gato-Habilidad
            // La llave indica que gato es y que habilidades le corresponden
            // El array de enteros son los valores siendo de la siguiente manera por valores
            // del array (0,1,2,3...)
            // |   0   |             1              |            2              |         3          |
            // | Vida  |  velocidad de movimiento   |   Cantidad de recompensa  |  Tipo de habilidad |
            // Dentro de la clase "Bullet" se instancia el daño y el rango de daño con el
            // tipo de municion (tambien existe tabla de correlacion)

            put(0 , new Double[] {   100.0   ,   0.5   ,   100.0   ,   100.0}); // Relacion de "Bastet"
            put(1 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Anubis"
            put(2 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Isis"
            put(3 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Horus"
            put(4 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Maahes"
            put(5 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Mafdet"
            put(6 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Mau"
            put(7 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Neftis"
            put(8 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Ra"
            put(9 , new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Sekhmet"
            put(10, new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Sobek"
            put(11, new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Thoth"
            put(12, new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Wadjet"
            put(13, new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Anput"
            put(14, new Double[] {  1.0   ,   1.0   ,   1.0   ,   1.0}); // Relacion de "Osiris"

        }
    };
    
    private Point position;
    private Double health;
    private Double speed;
    private Double reward;
    private int currentPathIndex;
    private Path currentPath;
    private Image catImage;
    
    public Cats(int typeOfCat, Path path) {
        this.position           = path.getPosition(0);
        this.health             = CatSkillCorrelation.get(typeOfCat)[HEALTH];
        this.speed              = CatSkillCorrelation.get(typeOfCat)[SPEED];
        this.reward             = CatSkillCorrelation.get(typeOfCat)[REWARD];
        this.currentPathIndex   = 1;
        this.currentPath        = path;
        this.catImage = new ImageIcon("java/src/main/resources/CharactersImages/Gato1.png").getImage();
    }
    
    public void update() {
        if(currentPathIndex <= currentPath.getLength()){
            double distance = position.distance(currentPath.getPosition(currentPathIndex));
            if(distance <= speed){
                position.setLocation(currentPath.getPosition(currentPathIndex));
                currentPathIndex++;
                System.out.println(currentPath.getPosition(currentPathIndex));
            }else{
                double dx = currentPath.getPosition(currentPathIndex).getX() - position.getX();
                double dy = currentPath.getPosition(currentPathIndex).getY() - position.getY();
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double directionX = dx / magnitude;
                double directionY = dy / magnitude;
                double displacementX = directionX * speed;
                double displacementY = directionY * speed;

                this.position.translate((int)Math.round(displacementX), (int)Math.round(displacementY));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(catImage, (int)position.getX(), (int)position.getY(),null);        
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
    
    public double getReward() {
        return reward;
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }
}
