import javax.swing.*;
import java.awt.*;

public class Ventana extends JPanel {

    final static int WIDTH = 1360;
    final static int HEIGHT = 768;
    public Menu menu = new Menu();
    public Ventana() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        add(menu);

        setLayout(null);

    }
}
