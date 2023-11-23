package turmite;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {
    Game game;

    SliderListener(Game gameInput){
        game = gameInput;
    }

    public void stateChanged(ChangeEvent e){
        JSlider slider = (JSlider)e.getSource();
        if(!slider.getValueIsAdjusting()){
            game.speed = slider.getValue();
        }
    }
}
