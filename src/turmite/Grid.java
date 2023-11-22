package turmite;

import java.io.Serializable;

//This class is used to store the grid, on which the turmites move.
public class Grid implements Serializable {
    //  The actual grid
    int[][] grid;
    //  Number of rows in the grid
    int row;
    //  Number of columns in the grid
    int column;

    //  Constructor
    Grid(int newRow, int newColumn) {
        row = newRow;
        column = newColumn;
        grid = new int[newRow][newColumn];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j] = 0;
            }
        }
    }
    public int getAtPosition(Position input){
        return grid[input.x][input.y];
    }
    public void setAtPosition(Position input, int set){
        grid[input.x][input.y] = set;
    }
}
