package turmite;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridClickActionListener implements ActionListener {

    Grid grid;
    GridButton button;


    GridClickActionListener(Grid gridInput, GridButton buttonInput){
        button = buttonInput;
        grid = gridInput;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(button.getBackground() != Color.RED){
            button.setBackground(Color.RED);
            grid.setAtPosition(button.p,2);
        } else{
            button.setBackground(Color.BLACK);
            grid.setAtPosition(button.p,0);
        }

    }
}
