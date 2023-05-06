import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class JUButton extends JComponent implements MouseListener {
    private int posX, posY;
    boolean clicked;
    private Image button;

    public JUButton(String PathImage, int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        button = new ImageIcon("java/src/main/resources/Rust icon.png").getImage();
        setBounds(this.posX, posY, button.getWidth(null), button.getHeight(null));
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(button, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clicked = true;
        System.out.println("pene pene pene");
    }
    @Override
    public void mouseReleased(MouseEvent e) { clicked = false; }
    @Override
    public void mouseEntered(MouseEvent e) {

        button = new ImageIcon("java/src/main/resources/erigei.jpg").getImage();
        setBounds(this.posX, posY, button.getWidth(null), button.getHeight(null));
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button = new ImageIcon("java/src/main/resources/Rust icon.png").getImage();
        setBounds(this.posX, posY, button.getWidth(null), button.getHeight(null));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}


}
