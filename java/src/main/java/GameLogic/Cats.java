package GameLogic;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Cats extends JComponent {
    private final int HEALTH = 0, SPEED = 1, REWARD = 2;
    private final Map<Integer, Double[]> CatSkillCorrelation = new HashMap<Integer, Double[]>() {
        {

            // Relacion de Gato-Habilidad
            // La llave indica que gato es y que habilidades le corresponden
            // El array de enteros son los valores siendo de la siguiente manera por valores
            // del array (0,1,2,3...)
            // | 0      |           1               |            2          |         3 
            // | Vida   | velocidad de movimiento   | Cantidad de recompensa| Tipo de habilidad
            // Dentro de la clase "Bullet" se instancia el daño y el rango de daño con el
            // tipo de municion (tambien existe tabla de correlacion)

            put(1, new Double[]     { 125.0 , 2.0   , 50.0  , 1.0   }); // Relacion de "Anubis"
            put(2, new Double[]     { 100.0 , 5.0   , 75.0  , 1.0   }); // Relacion de "Isis"
            put(3, new Double[]     { 170.0 , 2.4   , 30.0 , 1.0   }); // Relacion de "Horus"
            put(4, new Double[]     { 120.0 , 2.0   , 70.0 , 1.0   }); // Relacion de "Maahes"
            put(5, new Double[]     { 165.0   , 1.0   , 30.0   , 1.0   }); // Relacion de "Mafdet"
            put(6, new Double[]     { 150.0   , 1.4   , 20.0   , 1.0   }); // Relacion de "Mau"
            put(7, new Double[]     { 300.0   , 2.3  , 50.0   , 1.0   }); // Relacion de "Neftis"
            put(8, new Double[]     { 150.0   , 3.0   , 60.0   , 1.0   }); // Relacion de "Ra"
            put(9, new Double[]     { 200.0   , 2.5   , 80.0   , 1.0   }); // Relacion de "Sekhmet"
            put(10, new Double[]    { 1000.0   , 4.0   , 30.0   , 1.0   }); // Relacion de "Sobek"
            put(11, new Double[]    { 250.0   , 1.7   , 90.0   , 1.0   }); // Relacion de "Thoth"
            put(12, new Double[]    { 210.0   , 1.5   , 75.0   , 1.0   }); // Relacion de "Wadjet"
            put(13, new Double[]    { 390.0   , 2.0   , 110.0   , 1.0   }); // Relacion de "Anput"
            put(14, new Double[]    { 550.0  , 1.9   , 125.0   , 1.0   }); // Relacion de "Osiris"
            put(0, new Double[]     { 3000.0 , 0.5   , 0.0  , 1.0 }); // Relacion de "Bastet"

        }
    };

    private int posX, posY;
    private Double health;
    private Double speed;
    private Double reward;
    private int currentPathIndex;
    private final Path currentPath;
    private Image catImage;
    private String pathCatImage = "java/src/main/resources/CharactersImages/Gatos/";
    private Map<Integer, Clip> catGettingHitAudioMap;

    public Cats(int typeOfCat, Path path) {
        catGettingHitAudioMap = new HashMap<>(); // Inicializar el mapa de Clips de sonido

        try {
            File soundFile = new File("java/src/main/resources/Sounds/CatHit.wav");
            AudioInputStream AIS = AudioSystem.getAudioInputStream(soundFile);

            // Crear un Clip separado para cada gato y almacenarlo en el mapa
            Clip catGettingHitAudio = AudioSystem.getClip();
            catGettingHitAudio.open(AIS);
            catGettingHitAudioMap.put(typeOfCat, catGettingHitAudio);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        this.posX = (int) path.getFirst().getX();
        this.posY = (int) path.getFirst().getY();

        this.health = CatSkillCorrelation.get(typeOfCat)[HEALTH];
        this.speed = CatSkillCorrelation.get(typeOfCat)[SPEED];
        this.reward = CatSkillCorrelation.get(typeOfCat)[REWARD];
        this.currentPathIndex = 1;
        this.currentPath = path;
        this.catImage = new ImageIcon(pathCatImage + typeOfCat + ".png").getImage().getScaledInstance(60, 85, Image.SCALE_DEFAULT);

        setName(String.valueOf(typeOfCat));

        Gameplay.catsInMap.add(this);
    }

    public void run() {
        if (isDead()) {
            Levels.addToRewards(reward);
            setVisible(false);
        }
        if (currentPathIndex <= currentPath.getLength() - 1) {
            double distance = distance(currentPath.getPosition(currentPathIndex));
            if (distance <= speed) {
                posX = (int) currentPath.getPosition(currentPathIndex).getX();
                posY = (int) currentPath.getPosition(currentPathIndex).getY();
                currentPathIndex++;
            } else {
                double dx = currentPath.getPosition(currentPathIndex).getX() - posX;
                double dy = currentPath.getPosition(currentPathIndex).getY() - posY;
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double directionX = dx / magnitude;
                double directionY = dy / magnitude;
                double displacementX = directionX * speed;
                double displacementY = directionY * speed;

                posX += displacementX;
                posY += displacementY;
            }
        } else {
            setVisible(false);
            Levels.reduceHealthOfPlayer((int)(reward * 10));
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        
        // Ancho de la barra de vida actual
        int barWidth = Math.min(
        (int) (catImage.getWidth(null) * (health / CatSkillCorrelation.get(Integer.parseInt(getName()))[HEALTH]))
        // Se obtiene el ancho de la barra haciendo una regla de 3, dividiendo la vida actual con la vida maxima
        // Esto se multiplica por el ancho del imagen que es el ancho de la barra.
        ,catImage.getWidth(null));   
        // Esto se compara con el ancho de la barra por si termina siendo mayor, retorna el ancho y no sobre pasa el ancho de la imagen.     

        super.paintComponent(g);

        g.drawImage(catImage, posX - 30, posY - 42, null);

        // Barra de vida
        g.setColor(Color.RED);
        g.fillRect(posX - 30,  posY + (catImage.getHeight(null) - 10) - 42, catImage.getWidth(null), 10); // Dibujar barra vacía
        g.setColor(Color.GREEN);
        g.fillRect(posX - 30,  posY + (catImage.getHeight(null) - 10) - 42, barWidth, 10); // Dibujar relleno de la barra de vida
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void damage(Double amount) {
        health -= amount;
        
        int typeOfCat = Integer.parseInt(getName());

        // Obtener el Clip de sonido correspondiente al gato y reproducirlo
        Clip catGettingHitAudio = catGettingHitAudioMap.get(typeOfCat);
        if (catGettingHitAudio.isRunning()) {
            catGettingHitAudio.stop();
        }
        catGettingHitAudio.setFramePosition(0);
        catGettingHitAudio.start();
    }

    private int distance(Point point) {
        int dx = (int) (point.getX() - posX);
        int dy = (int) (point.getY() - posY);
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    public Point getPosition() {
        return new Point(posX, posY);
    }

    public double getReward() {
        return reward;
    }

    @Override
    public String toString() {
        return getName();
    }
}
