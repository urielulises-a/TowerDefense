package GameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

import main.Ventana;

public class Levels extends JComponent implements MouseListener {

    private final Path wayPath;
    private Image backgroundLevel;
    private int level;
    private ArrayList<HashMap<Point, Boolean>> availableSpots;
    private PanelPlayableCharacters PPC;                        // Panel para creacion de nuevas torres dentro del gamplay.

    public static boolean CharactersSelected = false;           // Variable de control para no empezar el nivel hasta que se hayan
                                                                // seleccionado los personajes.

    public Levels(int Level) {
        this.level = Level;
        this.wayPath = new Path(Level);
        this.backgroundLevel = new ImageIcon("java/src/main/resources/Levels/Nivel" + (Level + 1) + ".jpg").getImage();
        PPC = new PanelPlayableCharacters(this);
        add(PPC);

        setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);
        setVisible(true);
        setEnabled(true);
        setLayout(null);
        addMouseListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundLevel, 0, 0, null);

        if (availableSpots != null) {
            g.setColor(Color.YELLOW);
            for (HashMap<Point, Boolean> spot : availableSpots) {
                for (Point point : spot.keySet()) {
                    if (spot.get(point)) {
                        int x = (int) point.getX();
                        int y = (int) point.getY();
                        g.fillOval(x - 45, y - 45, 90, 90);

                    }
                }
            }
        }
    }

    public void startLevel() {
        Timer releaseNewCat = new Timer(5000, (e) -> {
            synchronized (Gameplay.catsInMap) {
                Cats newCat = new Cats(0, wayPath);
            }
        });

        releaseNewCat.setInitialDelay(5000);
        releaseNewCat.start();
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
            setAvailableSpots(null); // Si no hay botones seleccionados, no hay spots disponibles
        }
    }

    public void setIndexSelectedCharacters(ArrayList<Integer> indexOfSelectedCharacters){
        if(indexOfSelectedCharacters != null){
            PPC.setButtons(indexOfSelectedCharacters);
        }
    }

    public int getLevel() {
        return this.level;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(PPC.isAnyButtonSelected()                                                
        // Al dar click se verificara si hay un personaje seleccionado para introducir al mapa
        && TowerSpots.isInSpotRange(level, getMousePosition())                                                              
        // Si el click esta dentro de un spot para un nuevo perro
        && TowerSpots.isSpotAvailable(level, TowerSpots.getPointInRange(level, getMousePosition(CharactersSelected)))){
        // Si el spot esta disponible para introducir un nuevo perro

            Dogs newDog = new Dogs(TowerSpots.getPointInRange(level, getMousePosition()), PPC.getIndexButtonSelected());
            TowerSpots.changeSpotAvailability(level, getMousePosition(), false);
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
