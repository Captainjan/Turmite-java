package turmite;

import java.io.Serializable;
import java.util.ArrayList;

public class Turmite implements Serializable {

    Position currentPosition;
    //    Current position(x,y) of the turmite on the grid
    int direction;
    //    Direction shows which way the turmite is facing
//    1 -> NORTH
//    2 -> EAST
//    3 -> SOUTH
//    4 -> WEST
    int state;
    //    The state of the turmite can either be 0 or 1
    ArrayList<Pattern> patterns = new ArrayList<>();
    //    List of patterns, on which the turmite's movement is based on
    transient Game belongsTo;
//    Transient because there is no need to save this

    Turmite(Position newCurrentPosition, int newState, ArrayList<Pattern> newPatterns, Game input) {
        currentPosition = newCurrentPosition;
        direction = 1;
        state = newState;
        patterns = newPatterns;
        belongsTo = input;
    }
//    Basic constructor

    Turmite() {
    }

    void move() {
        boolean patternFound = false;
//        Change the color back from the indicator
        belongsTo.changeButtonAtPosition(currentPosition);
        for (int i = 0; i < patterns.size() && !patternFound; i++) {
            if (state == patterns.get(i).currentAntState && belongsTo.gameGrid.getAtPosition(currentPosition) == patterns.get(i).currentCellState && !patternFound) {
//                We only want to find one pattern, otherwise there can be issues
                patternFound = true;
                System.out.println(patterns.get(i));

//                Set the new states
                state = patterns.get(i).newAntState;
                belongsTo.gameGrid.setAtPosition(currentPosition, patterns.get(i).newCellState);
//                Change the color
                belongsTo.changeButtonAtPosition(currentPosition);

                changeDirection(patterns.get(i).direction);
                Position oldPosition = currentPosition;

                switch (direction) {
                    case 1:
                        if (currentPosition.x > 0) {
                            currentPosition.x--;
                            System.out.println("North");
                        }
                        break;
                    case 2:
                        if (currentPosition.y < belongsTo.gameGrid.column - 1) {
                            currentPosition.y++;
                            System.out.println("East");
                        }
                        break;
                    case 3:
                        if (currentPosition.x < belongsTo.gameGrid.row - 1) {
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
                if (checkObstacle()) {
                    System.out.println("Obstacle!");
                    currentPosition = oldPosition;
                }
            }
        }
    }

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

    public boolean checkObstacle() {
        return belongsTo.gameGrid.getAtPosition(currentPosition) == 2;
    }

    public void reverse() {
        if (direction == 1 || direction == 2) {
            direction = direction + 2;
        } else {
            direction = direction - 2;
        }
    }
}
