package turmite;

import java.io.Serializable;

public class Pattern implements Serializable {
    int currentAntState;
    int currentCellState;
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
    public String toString(){
        return currentAntState + "-" + currentCellState + "-" + direction + "-" + newAntState + "-" + newCellState;
    }
}
