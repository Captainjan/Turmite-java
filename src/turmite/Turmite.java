package turmite;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used to represent information about the individual turmites
 */
public class Turmite implements Serializable {
    /**
     * Current position(x,y) of the turmite on the grid
     */
    Position currentPosition;
    /**
     * Direction shows which way the turmite is facing
     * 1 -> NORTH
     * 2 -> EAST
     * 3 -> SOUTH
     * 4 -> WEST
     */
    int direction;
    /**
     * The state of the turmite can either be 0 or 1
     */
    int state;
    /**
     * List of patterns, on which the turmite's movement is based on
     */
    ArrayList<Pattern> patterns = new ArrayList<>();

    /**
     * The Game object the turmite belongs to, transient because there is no need to save this
     */
    transient Game game;

    /**
     * Basic constructor to create a turmite with the needed attributes, direction is set to 1 to start with
     */
    Turmite(Position newCurrentPosition, int newState, ArrayList<Pattern> newPatterns, Game gameInput) {
        currentPosition = newCurrentPosition;
        direction = 1;
        state = newState;
        patterns = newPatterns;
        game = gameInput;
    }
    /**
     * This function moves the turmite. It searches for the correct pattern based on the state of the turmite and
     * the state of the cell's state the turmite is on. Changes the states and the direction based on the pattern.
     * Also checks for obstacles and if there is one it reverses the direction of the turmite
     */
    void move() {
        boolean patternFound = false;
        game.changeColorAtPosition(currentPosition);
        for (int i = 0; i < patterns.size() && !patternFound; i++) {
            if (checkStates(patterns.get(i))) {
                patternFound = true;
                System.out.println(patterns.get(i));

                state = patterns.get(i).newAntState;
                game.gameGrid.setAtPosition(currentPosition, patterns.get(i).newCellState);
                game.changeColorAtPosition(currentPosition);

                changeDirection(patterns.get(i).direction);
                Position oldPosition = new Position(currentPosition.x, currentPosition.y);

                moveInDirection();

                if (checkObstacle()) {
                    System.out.println("Obstacle!");
                    reverse();
                    currentPosition = oldPosition;
                }
            }
        }
    }

    /**
     * Moves the turmite position on the grid based on the direction.
     * Checks to not let the turmite move out of bounds.
     */
    public void moveInDirection(){
        switch (direction) {
            case 1:
                if (currentPosition.x > 0) {
                    currentPosition.x--;
                    System.out.println("North");
                }
                break;
            case 2:
                if (currentPosition.y < game.gameGrid.column - 1) {
                    currentPosition.y++;
                    System.out.println("East");
                }
                break;
            case 3:
                if (currentPosition.x < game.gameGrid.row - 1) {
                    currentPosition.x++;
                    System.out.println("South");
                }
                break;
            case 4:
                if (currentPosition.y > 0) {
                    currentPosition.y--;
                    System.out.println("West");
                }
                break;
            default:
                break;
        }
    }

    /**
     * Changes the direction of the turmite
     * R -> Turn to the right,
     * L -> Turn to the left,
     * U -> Turn backwards,
     * Otherwise it doesn't change (if the input is N)
     */
    void changeDirection(char inputDirection) {
        switch (inputDirection) {
            case 'R':
                if (direction != 4) {
                    direction++;
                } else {
                    direction = 1;
                }
                break;
            case 'U':
                if (direction <= 2) {
                    direction = direction + 2;
                } else {
                    direction = direction - 2;
                }
                break;
            case 'L':
                if (direction != 1) {
                    direction--;
                } else {
                    direction = 4;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Returns true if the grid in the currentPosition equals 2, which means that it is an obstacle.
     */
    public boolean checkObstacle() {
        return game.gameGrid.getAtPosition(currentPosition) == 2;
    }

    /**
     * Reverses the direction of the turmite.
     */
    public void reverse() {
        if (direction == 1 || direction == 2) {
            direction = direction + 2;
        } else {
            direction = direction - 2;
        }
    }

    /**
     * Returns true if the states of the turmite and the cell where it is match the given pattern.
     */
    boolean checkStates(Pattern input){
        int currentAntState = input.currentAntState;
        int currentCellState = game.gameGrid.getAtPosition(currentPosition);
        return state == currentAntState && currentCellState == input.currentCellState;
    }
}
