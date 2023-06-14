package GameLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class PanelPlayableCharacters extends JPanel {

    private ArrayList<JToggleButton> buttons;
    private Levels thisLevel;

    public PanelPlayableCharacters(Levels currentLevel) {
        this.thisLevel = currentLevel;
        setBounds(450, 0, 450, 180);
        setLayout(new GridLayout(2, 2, 10, 10));
        setOpaque(false);
        buttons = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            JToggleButton characterButton = new JToggleButton();

            characterButton.setBorderPainted(false); // Sin bordes
            characterButton.setContentAreaFilled(false); // Sin fondo
            characterButton.setOpaque(false); // Fondo transparente.
            characterButton.setFocusPainted(false); // Sin fijar la selección (Código personalizado para el mismo fin)

            characterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JToggleButton button = (JToggleButton) e.getSource();
                    handleButtonSelection(button);
                }
            });
            buttons.add(characterButton);
            add(characterButton);
        }
    }

    private void handleButtonSelection(JToggleButton selectedButton) {
        for (JToggleButton button : buttons) {
            if (button != selectedButton) {
                button.setSelected(false);
            }
        }

        thisLevel.updateAvailableSpots(); // Actualizar los spots disponibles en Gameplay
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (JToggleButton button : buttons) {
            if (button.isSelected()) {
                // Dibujar un círculo amarillo en el centro del botón seleccionado
                int x = button.getX() + (button.getWidth() / 2) - (button.getIcon().getIconWidth() / 2); // Coordenada x
                                                                                                         // del centro
                                                                                                         // del círculo
                int diameter = button.getHeight(); // Diámetro del círculo del tamaño del alto del botón
                g.setColor(Color.YELLOW);
                g.fillOval(x, button.getY(), diameter, diameter);
            }
        }
    }

    public void setButtons(ArrayList<Integer> indexOfPlayableCharacters) {
        for (int i = 0; i < buttons.size(); i++) {
            JToggleButton button = buttons.get(i);
            button.setIcon(new ImageIcon(new ImageIcon(Dogs.getDogImagePath(indexOfPlayableCharacters.get(i)))
                    .getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));

            button.setText(Dogs.getCostOfDog(indexOfPlayableCharacters.get(i)));
            // Para poder observar cuanto cuesta un personaje , se incluye su costo como el texto del boton.
            button.setName(String.valueOf(indexOfPlayableCharacters.get(i)));
            // Se agrega de icono y de nombre al botón los valores elegidos por el usuario.
        }
    }

    public int getIndexButtonSelected() {
        for (JToggleButton button : buttons) {
            if (button.isSelected()) {
                return Integer.parseInt(button.getName());
            }
        }
        return -1;
    }

    public int getRewardButtonSelected(){
        for (JToggleButton button : buttons) {
            if (button.isSelected()) {
                return Integer.parseInt(button.getText());
            }
        }
        return -1;
    }

    public void selectedFunctionReady() {

        for (JToggleButton button : buttons) {
            button.setSelected(false);
        }

        thisLevel.updateAvailableSpots();
    }

    public boolean isAnyButtonSelected() {
        for (JToggleButton button : buttons) {
            if (button.isSelected()) {
                return true;
            }
        }
        return false;
    }

}
