import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu extends JComponent {
    private JUButton[] botonPlay = new JUButton[3];
    private Image backGround;
    private int imageWidth = 80;
    public Menu(){

        backGround = new ImageIcon("java/src/main/resources/beware.jpg").getImage();
        setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);

        for (int i = 0; i < 3; i++) {
//            getWidth() / 2,  (getHeight() / 3) * (i +1)
            botonPlay[i] = new JUButton("", (Ventana.WIDTH / 2) - imageWidth, (200 + 100) * (i + 1) - 300); // el 300 es arbitrario, solo lo puse para ver la separacion entre imagen
        }

        for (int i = 0; i < 3; i++) {

            add(botonPlay[i]);
        }

//        botonPlay = new JUButton("", 100, 100);
        setBounds(0,0,Ventana.WIDTH, Ventana.HEIGHT);
        // Instanciamiento boton play
//        add(botonPlay);

        opciones();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, Ventana.WIDTH, Ventana.HEIGHT, null);

    }
    private void opciones(){

        for (int i = 0; i < 3; i++) {

            if(botonPlay[i].clicked){
                JOptionPane.showMessageDialog(null, "¡Cuidado! Esto es una advertencia.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }



}
