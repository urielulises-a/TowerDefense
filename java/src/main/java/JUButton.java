import GameLogic.Path;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class JUButton extends JComponent implements MouseListener {
    private int posX, posY;
    private boolean clicked;
    private String PathImage;
    private Image button;

    public JUButton(String PathImage, int posX, int posY){

        this.posX = posX;
        this.posY = posY;
        this.PathImage = PathImage;
        button = new ImageIcon(this.PathImage).getImage();
        setBounds(this.posX, this.posY, button.getWidth(null), button.getHeight(null));
        addMouseListener(this);
    }

    public boolean isClicked() {
        return clicked;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(button, 0, 0, null);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        clicked = true;
        System.out.println("Mouse clicked");

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

        String UpdatedPath = PathImage.substring(0, PathImage.length() - 7);
        UpdatedPath = UpdatedPath + "On.png";
        button = new ImageIcon(UpdatedPath).getImage(); //Cuando el mouse entre se actualiza la imagen
        setBounds(this.posX, posY, button.getWidth(null), button.getHeight(null));
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {

        String UpdatedPath = PathImage.substring(0, PathImage.length() - 7);
        UpdatedPath = UpdatedPath + "Off.png"; //Obtiene la imagen de acuerdo a la accion del mouse
        System.out.println(UpdatedPath);
        button = new ImageIcon(UpdatedPath).getImage(); //Cuando el mouse salga se actualiza la imagen
        setBounds(this.posX, posY, button.getWidth(null), button.getHeight(null));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}


}
