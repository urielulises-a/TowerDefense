import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu extends JComponent {

    public static JUButton buttonStart;
    public static JUButton buttonCredits;
    private Image backGround;
    private int imageWidth = 80;
    public Menu(){

        backGround = new ImageIcon("java/src/main/resources/BackgroundMenu/SKETCH BUTTONS.jpg").getImage();
        setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);

        buttonStart = new JUButton("java/src/main/resources/Buttons/StartOff.png", (Ventana.WIDTH / 2) - imageWidth, (200 + 100) * 2 - 300);
        buttonCredits = new JUButton("java/src/main/resources/Buttons/CreditsOff.png", (Ventana.WIDTH / 2) - imageWidth, (200 + 100) * 3 - 300);

        add(buttonStart);
        add(buttonCredits);

//        botonPlay = new JUButton("", 100, 100);
        setBounds(0,0,Ventana.WIDTH, Ventana.HEIGHT);
        // Instanciamiento boton play

    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, Ventana.WIDTH, Ventana.HEIGHT, null);

    }
    public void opciones(){

    }

}
