package turmite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SaveActionListener implements ActionListener {

    Game game;

    SaveActionListener(Game input){
        game = input;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        try{
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
}
