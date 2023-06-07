package GameLogic;

import java.awt.Graphics;
import java.awt.Image;



import javax.swing.ImageIcon;
import javax.swing.JComponent;

import main.Ventana;


public class Levels extends JComponent{

    

    private final Path wayPath;
    private Image backgroundLevel;
    private int level;
    private int countOfCycles;

    public Levels(int Level) {
        this.countOfCycles = 0;
        this.level = Level;
        this.wayPath = new Path(Level);
        this.backgroundLevel = new ImageIcon("java/src/main/resources/Levels/Nivel1.jpg").getImage();
        
        setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);
        setVisible(true);
        setEnabled(true);
        setLayout(null);
        
        
    }

   @Override
   protected void paintComponent(Graphics g) {
       g.drawImage(backgroundLevel, 0, 0,null);
   }

    public void startLevel(){

        if(countOfCycles == 0 || countOfCycles == 500){

            Cats newCat = new Cats(0, wayPath);
            countOfCycles = 1;
        }else{
            countOfCycles++;
        }

            
    }



    

    
}
