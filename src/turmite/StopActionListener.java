package turmite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopActionListener implements ActionListener {

    Game game;

    StopActionListener(Game input){
        game = input;
    }

    /**
     * Flips the stopped boolean to its opposite value along with the colour of the stopbutton.
     */
    @Override
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton)e.getSource();
        if (game.stopped) {
            game.stopped = false;
            System.out.println("Go");
            button.setBackground(Color.GREEN);
        } else {
            game.stopped = true;
            System.out.println("Stop");
            button.setBackground(Color.RED);
        }
    }
}
