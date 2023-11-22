package turmite;

import java.io.Serializable;

public class Position implements Serializable {
    int x;
    int y;

    Position(int x_in, int y_in) {
        x = x_in;
        y = y_in;
    }
    public String toString(){
        return "Row: " + x + "Column: " + y;
    }
}
