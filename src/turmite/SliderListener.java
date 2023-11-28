package turmite;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {
    Game game;

    SliderListener(Game gameInput){
        game = gameInput;
    }

    /**
     * Sets the speed of the simulation with the slider's current value.
     */
    public void stateChanged(ChangeEvent e){
        JSlider slider = (JSlider)e.getSource();
        if(!slider.getValueIsAdjusting()){
            game.speed = slider.getValue();
        }
    }
}
