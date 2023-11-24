package turmite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ComboBoxActionListener implements ActionListener {

    JComboBox<String> menu;
    Game game;

    ComboBoxActionListener(Game gameInput, JComboBox<String> boxInput) {
        menu = boxInput;
        game = gameInput;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedOption = (String) menu.getSelectedItem();
        switch (selectedOption) {
            case "New game": {
                game.newGame();
            }
            break;
            case "Load game": {
                try {
                    game.stopped = true;

                    FileInputStream f = new FileInputStream("grid.txt");
                    ObjectInputStream gridIn = new ObjectInputStream(f);

                    game.gameGrid = (Grid) gridIn.readObject();
                    game.reloadGrid();

                    FileInputStream g = new FileInputStream("turmite.txt");
                    ObjectInputStream turmiteIn = new ObjectInputStream(g);

                    game.turmiteList.clear();
                    game.turmiteList = (ArrayList<Turmite>) turmiteIn.readObject();

                    for (Turmite t : game.turmiteList) {
                        t.game = game;
                    }
                    game.stopped = false;

                } catch (ClassNotFoundException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            break;
            case "Save game": {
                try {
                    FileOutputStream f = new FileOutputStream("grid.txt");
                    ObjectOutputStream gridOut = new ObjectOutputStream(f);
                    gridOut.writeObject(game.gameGrid);
                    gridOut.close();

                    FileOutputStream g = new FileOutputStream("turmite.txt");
                    ObjectOutputStream turmiteOut = new ObjectOutputStream(g);
                    turmiteOut.writeObject(game.turmiteList);
                    turmiteOut.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            break;
            default:
                break;
        }
    }
}
