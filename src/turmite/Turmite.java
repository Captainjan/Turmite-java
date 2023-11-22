package turmite;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Turmite {
    Position currentPosition;
    int direction;
    int state;
    ArrayList<Pattern> patterns = new ArrayList<>();

    Game belongsTo;

    Turmite(Position newCurrentPosition, int newState, ArrayList<Pattern> newPatterns, Game input) {
        currentPosition = newCurrentPosition;
        direction = 1;
        state = newState;
        patterns = newPatterns;
        belongsTo = input;
    }

    Turmite() {
    }

    void move() {
        boolean patternFound = false;
        for (Pattern p : patterns) {
            if (state == p.currentAntState && belongsTo.gameGrid.getAtPosition(currentPosition) == p.currentCellState && !patternFound) {

                System.out.println(p);
                patternFound = true;

                state = p.newAntState;
                System.out.println(state);

                System.out.println("before " + belongsTo.gameGrid.getAtPosition(currentPosition));
                belongsTo.gameGrid.setAtPosition(currentPosition, p.newCellState);
                System.out.println("after " + belongsTo.gameGrid.getAtPosition(currentPosition));

                belongsTo.changeButtonAtPosition(belongsTo.gridPanel, currentPosition);

                changeDirection(p.direction);
                Position oldPosition = currentPosition;

                switch (direction) {
                    case 1:
                        if (currentPosition.x < belongsTo.gameGrid.row) {
                            currentPosition.x++;
                        }
                    case 2:
                        if (currentPosition.y < belongsTo.gameGrid.column) {
                            currentPosition.y++;
                        }
                    case 3:
                        if (currentPosition.x > 0) {
                            currentPosition.x--;
                        }
                    case 4:
                        if (currentPosition.y > 0) {
                            currentPosition.y--;
                        }
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
                if (direction < 4) {
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
                if (direction > 1) {
                    direction--;
                } else {
                    direction = 4;
                }
            default:
                break;
        }
    }

    public boolean checkObstacle() {
        return belongsTo.gameGrid.getAtPosition(currentPosition) == 3;
    }

}
