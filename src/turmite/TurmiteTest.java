package turmite;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TurmiteTest {
    Game game;

    Position startpos = new Position(40, 40);

    ArrayList<turmite.Pattern> patterns = new ArrayList<>();
    Turmite t = new Turmite(startpos,0,patterns,game);

    @Test
    public void move() {
        game = new Game();
        t.patterns.add(new turmite.Pattern(0, 0, 'R', 1, 0));
        t.patterns.add(new turmite.Pattern(0, 1, 'R', 1, 1));
        t.patterns.add(new turmite.Pattern(1, 0, 'N', 0, 0));
        t.patterns.add(new turmite.Pattern(1, 1, 'N', 0, 1));
        t = new Turmite(startpos,0,patterns,game);
        t.move();
        Position p = new Position(40,40);
        assertEquals(2, t.direction);
        assertEquals(1, game.gameGrid.getAtPosition(p));
        assertEquals(0, t.state);
        assertEquals(Color.WHITE,game.getColorAtPosition(p));
    }

    @Test
    public void moveInDirection() {
        game = new Game();
        t = new Turmite(startpos, 0, patterns, game);
        t.moveInDirection();
        assertEquals(new Position(39, 40), t.currentPosition);
        t.direction = 2;
        t.moveInDirection();
        assertEquals(new Position(39, 41), t.currentPosition);
        t.direction = 3;
        t.moveInDirection();
        assertEquals(new Position(40, 41), t.currentPosition);
        t.direction = 4;
        t.moveInDirection();
        assertEquals(new Position(40, 40), t.currentPosition);
    }

    @Test
    public void changeDirection() {
        game = new Game();
        t = new Turmite(startpos, 0, patterns, game);
        t.changeDirection('R');
        assertEquals(2, t.direction);
        t.changeDirection('L');
        assertEquals(1, t.direction);
        t.changeDirection('N');
        assertEquals(1, t.direction);
        t.changeDirection('U');
        assertEquals(3, t.direction);
    }

    @Test
    public void checkObstacle() {
        game = new Game();
        game.gameGrid.grid[40][40] = 2;
        t = new Turmite(startpos, 0, patterns, game);
        assertTrue(t.checkObstacle());
        game.gameGrid.grid[40][40] = 0;
        assertFalse(t.checkObstacle());
    }

    @Test
    public void reverse() {
        game = new Game();
        t = new Turmite(startpos, 0, patterns, game);
        t.reverse();
        assertEquals(3,t.direction);
        t.reverse();
        assertEquals(1,t.direction);
        t.direction = 2;
        t.reverse();
        assertEquals(4,t.direction);
        t.reverse();
        assertEquals(2,t.direction);
    }

    @Test
    public void checkStates() {
        game = new Game();
        t = new Turmite(startpos,0,patterns,game);
        t.patterns.add(new turmite.Pattern(0, 0, 'R', 1, 0));
        t.patterns.add(new turmite.Pattern(0, 1, 'R', 1, 1));
        t.patterns.add(new turmite.Pattern(1, 0, 'N', 0, 0));
        t.patterns.add(new turmite.Pattern(1, 1, 'N', 0, 1));
        assertTrue(t.checkStates(t.patterns.get(0)));
        assertFalse(t.checkStates(t.patterns.get(1)));
        assertFalse(t.checkStates(t.patterns.get(2)));
        assertFalse(t.checkStates(t.patterns.get(3)));
    }
}