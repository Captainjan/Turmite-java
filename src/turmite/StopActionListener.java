package turmite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopActionListener implements ActionListener {

    Game game;

    StopActionListener(Game input){
        game = input;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (game.stopped) {
            game.stopped = false;
            System.out.println("Go");
        } else {
            game.stopped = true;
            System.out.println("Stop");
        }
    }
}
