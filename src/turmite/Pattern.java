package turmite;

import java.io.Serializable;

/**
 * Stores a movement pattern by which the turmites move.
 */
public class Pattern implements Serializable {
    /**
     * Either 0 or 1
     */
    int currentAntState;
    /**
     * Either 0 or 1
     */
    int currentCellState;
    /**
     * Stores which way the turmite should turn. Possible values: R -> right, L -> left, N -> no turn, U -> back turn
     */
    char direction;
    /**
     * Either 0 or 1
     */
    int newAntState;
    /**
     * Either 0 or 1
     */
    int newCellState;

    /**
     * Basic constructor
     */
    public Pattern(int a, int b, char c, int d, int e) {
        currentAntState = a;
        currentCellState = b;
        direction = c;
        newCellState = d;
        newAntState = e;
    }

    Pattern() {
    }

    /**
     * Checks if the given pattern's values fit the specification. Only returns true if they do.
     */
    public boolean checkPatternValidity() {
        if (!(currentAntState == 0 || currentAntState == 1)) {
            return false;
        }

        if (!(currentCellState == 0 || currentCellState == 1)) {
            return false;
        }

        if (!(direction == 'R' || direction == 'L' || direction == 'N' || direction == 'U')) {
            return false;
        }

        if (!(newCellState == 0 || newCellState == 1)) {
            return false;
        }
        return true;
    }
    /**
     * Used to print out patterns.
     */
    public String toString(){
        return currentAntState + "-" + currentCellState + "-" + direction + "-" + newCellState + "-" + newAntState;
    }
}
