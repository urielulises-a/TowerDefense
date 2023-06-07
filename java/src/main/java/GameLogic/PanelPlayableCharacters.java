package GameLogic;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;


public class PanelPlayableCharacters extends JComponent {
    private ArrayList<JButton> listOfCharactersToPlay;


    public PanelPlayableCharacters(ArrayList<Integer> indexOfPlayableCharacters) {
        
        listOfCharactersToPlay = new ArrayList<>();

        for (int i = 0; i < indexOfPlayableCharacters.size(); i++) {
            String characterImagePath = "java/src/main/resources/CharactersImages/Perro" + indexOfPlayableCharacters.get(i) + ".png";
            JButton characterButton = new JButton(  new ImageIcon(
                                                    new ImageIcon(characterImagePath).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            listOfCharactersToPlay.add(characterButton);
            listOfCharactersToPlay.get(i).addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("me apretaron we");
                }
                
            });
            add(characterButton);
        }

        setBounds(450, 0, 450, 180);
        setLayout(new GridLayout(4,1));
    }


}

