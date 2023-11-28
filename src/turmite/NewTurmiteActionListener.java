package turmite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewTurmiteActionListener implements ActionListener {
    Game game;
    NewTurmiteActionListener(Game input){
        game = input;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        game.newTurmite();
    }
}
