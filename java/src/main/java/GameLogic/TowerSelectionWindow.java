package GameLogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TowerSelectionWindow extends JFrame {

    private JButton[] listOfCharactersToPlay;
    private ArrayList<Integer> indexSelected;

    public TowerSelectionWindow(int level) {
        listOfCharactersToPlay = new JButton[14]; // 14 personajes jugables
        indexSelected = new ArrayList<Integer>();

        setTitle("Selección de Torres");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(3, 5));

        for (int i = 0; i < listOfCharactersToPlay.length; i++) {
            String characterImagePath = "java/src/main/resources/CharactersImages/Perro" + i + ".png";
            listOfCharactersToPlay[i] = createCharacterButton(characterImagePath, i);
            add(listOfCharactersToPlay[i]);
        }

        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    private JButton createCharacterButton(String imagePath, int characterIndex) {
        JButton button = new JButton(new ImageIcon(new ImageIcon(imagePath)
                .getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));

        button.addActionListener(new ActionListener() {
            private boolean isSelected = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                isSelected = !isSelected;

                if (isSelected) {
                    indexSelected.add(characterIndex);
                    button.setBackground(Color.GREEN);
                } else {
                    indexSelected.remove(Integer.valueOf(characterIndex));
                    button.setBackground(null);
                }

                if (indexSelected.size() >= 4) {
                    JOptionPane.showMessageDialog(null, "Has seleccionado los personajes requeridos.");
                    Gameplay.CharactersSelected = true;
                }
            }
        });

        return button;
    }

    public ArrayList<Integer> getSelectedCharacters() {
        return indexSelected;
    }
}
