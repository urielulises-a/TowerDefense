package GameLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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


    public Gameplay() {

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
