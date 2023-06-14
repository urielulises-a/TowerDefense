package GameLogic;

import main.Ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu extends JPanel {

    private Image backGround;
    private JTextField textField;

    public UserMenu() {
        backGround = new ImageIcon("java/src/main/resources/User/UserData.png").getImage();
        setLayout(null);
        setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);

        textField = new JTextField();
        textField.setBounds(Ventana.WIDTH/4, Ventana.HEIGHT/2 + 150, Ventana.WIDTH/2, 30);
        add(textField);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, Ventana.WIDTH, Ventana.HEIGHT, null);
    }

    public void addActionListener(ActionListener listener) {
        textField.addActionListener(listener);
    }

    public String getInputText() {
        return textField.getText();
    }

    public void clearInputText() {
        textField.setText("");
    }
}
