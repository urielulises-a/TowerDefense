package GameLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.ecodeup.jdbc.DataBase;

import main.Ventana;

// [ ] - Agregar sonidos
// [ ] - Refinar niveles
// [ ] - Terminar de configurar perros y gatos
// [ ] - ¿Animaciones entre niveles? (Explicacion de la historia)
// [ ] - Cambio de nivel (dejar de hardcodear el nivel)
// [ ] - IMAGENES
// [x] - Agregar que se pueda perder.

public class Gameplay extends JComponent {

    public static ArrayList<Dogs> dogsInMap; // Variable para saber los perros que hay en juego
    public static ArrayList<Cats> catsInMap; // Variable para saber los gatos que hay en juego
    public static ArrayList<Bullet> bulletsInMap; // Variable para saber las municiones que hay en juego
    private Levels actualLevel; // Variable en la que se instancia el nivel a jugar (Path para los gatos y los
    // gatos a salir.)
    private Timer runLevel; // Timer para renovar el gamplay a 60 FPS
    private TowerSelectionWindow TSW; // Seleccion de perros de inicio
    private boolean panelCreated = false; // Variable de control
    private AudioInputStream AIS;
    private Clip levelSuccessSound;
    private Clip levelFailedSound;



    public Gameplay() {

        try {
            AIS = AudioSystem.getAudioInputStream(new File("java/src/main/resources/Sounds/levelSuccessSound.wav"));
            levelSuccessSound =  AudioSystem.getClip();
            levelSuccessSound.open(AIS);

            // Establece el volumen del Clip (de 0.0 a 1.0)
            float volumen = 0.1f; // Volumen al 10%
            FloatControl control = (FloatControl) levelSuccessSound.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volumen) / Math.log(10.0) * 20.0);
            control.setValue(dB);

            AIS = AudioSystem.getAudioInputStream(new File("java/src/main/resources/Sounds/levelFailedSound.wav"));
            levelFailedSound =  AudioSystem.getClip();
            levelFailedSound.open(AIS);

            control = (FloatControl) levelFailedSound.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(dB);


        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        dogsInMap = new ArrayList<>();
        catsInMap = new ArrayList<>();
        bulletsInMap = new ArrayList<>();
        actualLevel = new Levels(DataBase.level);
        runLevel = new Timer("Run Level Timer");

        setPreferredSize(new Dimension(Ventana.WIDTH, Ventana.HEIGHT));
        setLayout(null);

        TSW = new TowerSelectionWindow(actualLevel.getLevel());

        add(actualLevel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Cats cats : catsInMap) {
            cats.paintComponent(g);
        }
        for (Dogs dogs : dogsInMap) {
            dogs.paintComponent(g);
        }
        for (Bullet bullets : bulletsInMap) {
            bullets.paintComponent(g);
        }
    }

    public void run() {
        TSW.toFront();

        runLevel.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Levels.getHealthOfPlayer() <= 0) {
                    Levels.battleMusic.stop();
                    levelFailedSound.start();

                    if (dogsInMap.size() > 0) {
                        dogsInMap.clear();
                    }
                    if (catsInMap.size() > 0) {
                        catsInMap.clear();
                    }
                    if (bulletsInMap.size() > 0) {
                        bulletsInMap.clear();
                    }

                    int choice = JOptionPane.showOptionDialog(getTopLevelAncestor(), "¡Has perdido!",
                            "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                            new String[] { "Salir.", "Reiniciar Nivel" }, "Salir.");

                    if (choice == JOptionPane.YES_OPTION) {
                        // Volver al menú principal
                        System.exit(0);
                    } else if (choice == JOptionPane.NO_OPTION) {
                        // Reiniciar el nivel
                        resetLevel();
                    }
                }

                if (actualLevel.isLevelComplete() && catsInMap.size() <= 0) {
                    Levels.battleMusic.stop();
                    levelSuccessSound.start();
                    int choice = JOptionPane.showOptionDialog(getTopLevelAncestor(), "¡Nivel superado!",
                            "¿Siguiente nivel?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                            new String[] { "Salir.", "Siguiente Nivel" }, "Salir.");

                    if (choice == JOptionPane.YES_OPTION) {
                        // Salir.
                        JOptionPane.showMessageDialog(getTopLevelAncestor(), "Saliendo de MithPets.");
                        System.exit(0);
                    } else if (choice == JOptionPane.NO_OPTION) {
                        // Siguiente nivel
                        nextLevel();

                    }

                }

                if (TowerSelectionWindow.CharactersSelected) {

                    actualLevel.levelLogic();

                    if (!panelCreated) {
                        actualLevel.setIndexSelectedCharacters(TSW.getSelectedCharacters());
                        panelCreated = true;
                    }
                    synchronized (catsInMap) {
                        for (Cats cats : catsInMap) {
                            cats.run();
                        }
                    }
                    synchronized (dogsInMap) {
                        for (Dogs dogs : dogsInMap) {
                            dogs.update();
                        }
                    }
                    synchronized (bulletsInMap) {
                        for (Bullet bullets : bulletsInMap) {
                            bullets.update();
                        }
                    }

                    catsInMap.removeIf(cats -> !cats.isVisible());
                    repaint();

                }

            }
        }, 0, 16);

    }

    public void resetLevel() {

        levelFailedSound.stop();
        levelFailedSound.setFramePosition(0);

        for (Dogs dog : dogsInMap) {
            dog.stopAtack();
        }

        dogsInMap.clear();
        catsInMap.clear();
        bulletsInMap.clear();
        

        TowerSpots.resetSpots(actualLevel.getLevel());
        panelCreated = false;
        TSW = new TowerSelectionWindow(actualLevel.getLevel());
        
        remove(actualLevel);

        actualLevel = new Levels(actualLevel.getLevel());
        add(actualLevel);

    }

    public void nextLevel() {
        levelSuccessSound.stop();
        levelSuccessSound.setFramePosition(0);
        
        int nextLevel = actualLevel.getLevel() + 1;


        for (Dogs dog : dogsInMap) {
            dog.stopAtack();
        }

        dogsInMap.clear();
        catsInMap.clear();
        bulletsInMap.clear();
        
        try {
            DataBase.updateUser(nextLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        panelCreated = false;
        TSW = new TowerSelectionWindow(nextLevel);
        
        remove(actualLevel);
        actualLevel = new Levels(nextLevel);
        add(actualLevel);
    }

}
