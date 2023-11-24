package turmite;

import java.io.Serializable;

/**
 * Stores a movement pattern by which the turmites move.
 */
public class Pattern implements Serializable {
    int currentAntState;
    int currentCellState;
    /**
     * Stores which way the turmite should turn. Possible values: R -> right, L -> left, N -> no turn, U -> back turn
     */
    char direction;
    int newAntState;
    int newCellState;

    Pattern(int a, int b, char c, int d, int e) {
        currentAntState = a;
        currentCellState = b;
        direction = c;
        newAntState = d;
        newCellState = e;
    }

    Pattern() {
    }

    /**
     * Used to print out patterns.
     */
    public String toString(){
        return currentAntState + "-" + currentCellState + "-" + direction + "-" + newAntState + "-" + newCellState;
    }
}
