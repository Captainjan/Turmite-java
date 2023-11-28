package turmite;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    Game game;

    Position startpos = new Position(40, 40);

    @Test
    public void changeButtonAtPosition() {
        game = new Game();
        assertEquals(Color.BLACK, game.getColorAtPosition(startpos));
        game.gameGrid.setAtPosition(startpos, 1);
        game.changeColorAtPosition(startpos);
        assertEquals(Color.WHITE, game.getColorAtPosition(startpos));
        game.gameGrid.setAtPosition(startpos, 2);
        game.changeColorAtPosition(startpos);
        assertEquals(Color.RED, game.getColorAtPosition(startpos));
    }

    @Test
    public void indicateTurmite() {
        game = new Game();
        game.indicateTurmite(startpos);
        assertEquals(Color.GREEN, game.getColorAtPosition(startpos));
    }

    @Test
    public void checkOverlap() {
        ArrayList<Pattern> patterns = new ArrayList<>();
        game = new Game();
        Turmite t = new Turmite(startpos, 0, patterns, game);
        Turmite q = new Turmite(startpos, 0, patterns, game);
        game.turmiteList.add(t);
        game.turmiteList.add(q);
        game.checkOverlap(t);
        assertEquals(3,t.direction);
        assertEquals(3,q.direction);
        game.turmiteList.remove(q);
        q = new Turmite(startpos, 1, patterns, game);
        game.turmiteList.add(q);
        game.checkOverlap(t);
        assertFalse(game.turmiteList.contains(t));
    }

}