package turmite;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridClickActionListener implements ActionListener {

    Grid grid;


    GridClickActionListener(Grid gridInput){
        grid = gridInput;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        GridButton button = (GridButton) e.getSource();
        if(button.getBackground() != Color.RED){
            button.setBackground(Color.RED);
            grid.setAtPosition(button.p,2);
        } else{
            button.setBackground(Color.BLACK);
            grid.setAtPosition(button.p,0);
        }

    }
}
