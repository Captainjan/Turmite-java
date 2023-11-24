package turmite;

import java.io.Serializable;

/**
 * This class is used to store the grid, on which the turmites move.
 */
public class Grid implements Serializable {
    /**
     *  2D grid where the turmites move.
     */
    int[][] grid;
    /**
     * Number of rows in the grid.
     */
    int row;
    /**
     * Number of columns in the grid.
     */
    int column;

    /**
     * Creates a grid with the given row and column parameters and sets the values to be 0.
     */
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
    /**
     * Returns the value of the grid at the given position.
     */
    public int getAtPosition(Position input){
        return grid[input.x][input.y];
    }

    /**
     * Sets the value of the grid at the given position to the given int value.
     */
    public void setAtPosition(Position input, int set){
        grid[input.x][input.y] = set;
    }
}
