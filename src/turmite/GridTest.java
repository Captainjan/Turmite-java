package turmite;

import org.junit.Test;

import static org.junit.Assert.*;

public class GridTest {

    Grid grid;

    Position startpos = new Position(40,40);

    @Test
    public void getAtPosition() {
        grid = new Grid(80,80);
        assertEquals(0,grid.getAtPosition(startpos));
        grid.grid[40][40] = 1;
        assertEquals(1,grid.getAtPosition(startpos));
    }

    @Test
    public void setAtPosition() {
        grid = new Grid(80,80);
        grid.setAtPosition(startpos,1);
        assertEquals(1,grid.grid[40][40]);
        grid.setAtPosition(startpos, 0);
        assertEquals(0,grid.grid[40][40]);
    }
}