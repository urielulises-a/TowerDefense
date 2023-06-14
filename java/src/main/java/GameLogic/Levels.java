package GameLogic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import main.Ventana;

public class Levels extends JComponent implements MouseListener {
    private final int NUM_ENEMIES_TO_GENERATE = 0, DELAY_GENERATION = 1, MAXIMUM_ENEMY_DIFFICULTY = 2,
            HEALTH_MAX = 10000;
    private final Map<Integer, Integer[]> levelCorrelationGenerator = new HashMap<Integer, Integer[]>() {
        {

            // Relacion de niveles
            // La llave indica que nivel es y que estadisticas le relacionan
            // El array de enteros son los valores siendo de la siguiente manera por valores
            // del array (0,1,2,3...)
            // | 0 | 1 | 2 |
            // | Numero de enemigos a generar | Delay de generacion de enemigos(ms) |
            // Dificultad maxima de gato (0-14) |

            put(0, new Integer[] { 20, 3500, 4 }); // Nivel 1 Neighborhood
            put(1, new Integer[] { 30, 1000, 9 }); // Nivel 2
            put(2, new Integer[] { 50, 500, 14 }); // Nivel 3 Hell

        }
    };

    private static int healthOfPlayer;
    private static int rewardsEarned;
    private int delayOfStartLevel;
    private int enemiesToGenerate;
    private int enemiesGenerated;
    private int delayGeneration;
    private int enemyDifficulty;
    private int level;
    private boolean alreadyRunning = false; // Variable para hacer control
    private boolean levelComplete;
    private Path wayPath;
    private Image backgroundLevel;
    private ArrayList<HashMap<Point, Boolean>> availableSpots;
    private PanelPlayableCharacters PPC;

    public Levels(int Level) {

        this.level = Level;
        Levels.healthOfPlayer = this.HEALTH_MAX;
        Levels.rewardsEarned = 100;
        this.levelComplete = false;
        this.delayOfStartLevel = 5000;
        this.enemiesGenerated = 0;
        this.enemiesToGenerate = levelCorrelationGenerator.get(level)[NUM_ENEMIES_TO_GENERATE];
        this.delayGeneration = levelCorrelationGenerator.get(level)[DELAY_GENERATION];
        this.enemyDifficulty = levelCorrelationGenerator.get(level)[MAXIMUM_ENEMY_DIFFICULTY];
        this.wayPath = new Path(Level);
        this.backgroundLevel = new ImageIcon("java/src/main/resources/Levels/Nivel " + (Level + 1) + ".png").getImage();
        this.PPC = new PanelPlayableCharacters(this);

        add(PPC);

        setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);
        setVisible(true);
        setEnabled(true);
        setLayout(null);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(backgroundLevel, 0, 0, null);

        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial Bond", Font.BOLD, 24));
        g2d.drawString("Dinero actual: $" + String.valueOf(rewardsEarned), 550, 200);

        if (availableSpots != null) {
            for (HashMap<Point, Boolean> spot : availableSpots) {
                for (Point point : spot.keySet()) {
                    if (PPC.isAnyButtonSelected() && spot.get(point)) {
                        g2d.setColor(new Color(255, 255, 0, 128)); // Amarillo opaco (con transparencia)
                        int x = (int) point.getX();
                        int y = (int) point.getY();
                        g2d.fillOval(x - 45, y - 45, 90, 90);
                    }
                }
            }
        }

        int healthPercentage = (int) ((Levels.healthOfPlayer * 100) / HEALTH_MAX);
        int barWidth = (healthPercentage * Ventana.WIDTH) / 100;

        g2d.setColor(Color.RED);
        g2d.fillRect(0, Ventana.HEIGHT - 50, Ventana.WIDTH, 50); // Dibujar barra vacía
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, Ventana.HEIGHT - 50, barWidth, 50); // Dibujar relleno de la barra de vida
    }

    public void levelLogic() {
        if (alreadyRunning) {
            return;
        }

        Timer releaseNewCat = new Timer("Timer para control de generacion de niveles.");
        releaseNewCat.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Levels.healthOfPlayer <= 0) {
                    this.cancel();
                }

                synchronized (Gameplay.catsInMap) {
                    if (enemiesGenerated < enemiesToGenerate) {
                        Cats newCat = new Cats((int) (Math.random() * enemyDifficulty), wayPath);
                        enemiesGenerated++;
                    } else {
                        levelComplete = true;
                    }
                }

            }
        }, this.delayOfStartLevel, this.delayGeneration);

        alreadyRunning = true;

    }

    public void setAvailableSpots(ArrayList<HashMap<Point, Boolean>> spots) {
        availableSpots = spots;
        repaint();
    }

    public void updateAvailableSpots() {
        if (PPC.isAnyButtonSelected()) {
            ArrayList<HashMap<Point, Boolean>> spots = TowerSpots.correlationTowersSpotAvailable.get(level);
            setAvailableSpots(spots);
        } else {
            setAvailableSpots(null);
        }
    }

    public void setIndexSelectedCharacters(ArrayList<Integer> indexOfSelectedCharacters) {
        if (indexOfSelectedCharacters != null) {
            PPC.setButtons(indexOfSelectedCharacters);
        }
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isLevelComplete() {
        return levelComplete;
    }

    public static int getHealthOfPlayer() {
        return healthOfPlayer;
    }

    public static void reduceHealthOfPlayer(int amountToReduce) {
        healthOfPlayer -= amountToReduce;
    }

    public static int getRewards() {
        return rewardsEarned;
    }

    public static void addToRewards(double amountToIncrease) {
        rewardsEarned += amountToIncrease;
    }

    public static void reduceRewards(double amountToDecrese) {
        rewardsEarned -= amountToDecrese;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point closesSpotToMouse = TowerSpots.getPointInRange(level, getMousePosition());
        boolean isInSpotRange = TowerSpots.isInSpotRange(level, getMousePosition());
        boolean isSpotAvailable = TowerSpots.isSpotAvailable(level, closesSpotToMouse);

        if (Gameplay.dogsInMap.size() > 0) {
            for (Dogs dogs : Gameplay.dogsInMap) {
                if (dogs.clickInDog(getMousePosition())) {
                    TowerSpots.changeSpotAvailability(level, dogs.getPosition(), true);
                    dogs.setSelected(true);
                    dogs.dogsOptions.show(this, e.getX(), e.getY());
                    
                }
            }
        }

        if (PPC.isAnyButtonSelected() && isInSpotRange && isSpotAvailable) {
            // Primera revision -> Se selecciono un personaje del panel y el siguiente click
            // fue en un spot para perros.
            if (rewardsEarned >= PPC.getRewardButtonSelected()) {
                // Se verifica si es posible obtener el perro.
                reduceRewards(PPC.getRewardButtonSelected());
                Dogs newDog = new Dogs(closesSpotToMouse, PPC.getIndexButtonSelected());
                TowerSpots.changeSpotAvailability(level, closesSpotToMouse, false);
                PPC.selectedFunctionReady();
            } else {
                PPC.selectedFunctionReady();
            }

        } else if (PPC.isAnyButtonSelected() && !isInSpotRange) {
            PPC.selectedFunctionReady();
        }

        

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
