package turmite;

import java.io.Serializable;

/**
 * Stores 2 coordinates to specify a position on the 2D grid
 */
public class Position implements Serializable {
    int x;
    int y;

    /**
     * Basic constructor that sets the given coordinates.
     */
    Position(int xInput, int yInput) {
        x = xInput;
        y = yInput;
    }

    /**
     * Used when printing positions out.
     */
    public String toString(){
        return "Row: " + x + "Column: " + y;
    }
}
