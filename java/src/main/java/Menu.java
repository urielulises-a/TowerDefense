import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu extends JComponent {

    public static JUButton buttonPlay;
    public static JUButton buttonCredits;
    private Image backGround;
    private int imageWidth = 80;
    public Menu(){

        backGround = new ImageIcon("java/src/main/resources/beware.jpg").getImage();
        setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);

        buttonPlay = new JUButton("", (Ventana.WIDTH / 2) - imageWidth, (200 + 100) * 2 - 300);
        buttonCredits = new JUButton("", (Ventana.WIDTH / 2) - imageWidth, (200 + 100) * 3 - 300);

        add(buttonPlay);
        add(buttonCredits);

//        botonPlay = new JUButton("", 100, 100);
        setBounds(0,0,Ventana.WIDTH, Ventana.HEIGHT);
        // Instanciamiento boton play
//        add(botonPlay);

    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, Ventana.WIDTH, Ventana.HEIGHT, null);

    }
    public void opciones(){

    }

}
