package turmite;

import javax.swing.*;

/**
 * The type of button that makes up the graphical grid, it stores its own location
 */
public class GridButton extends JButton {
    /**
     * Location on the grid
     */
    Position p;

    /**
     * Basic constructor with the input position
     */
    GridButton(Position input) {
        p = input;
    }
}
