package turmite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.util.ArrayList;

public class LoadActionListener implements ActionListener {
    Game game;

    LoadActionListener(Game input){
        game = input;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            game.stopped = true;
            FileInputStream f = new FileInputStream("grid.txt");
            ObjectInputStream gridIn = new ObjectInputStream(f);
            game.gameGrid = (Grid)gridIn.readObject();
            game.reloadGrid();

            FileInputStream g = new FileInputStream("turmite.txt");
            ObjectInputStream turmiteIn = new ObjectInputStream(g);
            ArrayList<Turmite> input = new ArrayList<>();
            input = (ArrayList<Turmite>)turmiteIn.readObject();
            game.turmiteList = input;

            for(Turmite t : game.turmiteList){
                t.belongsTo = game;
            }
            game.stopped = false;

        } catch (ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
