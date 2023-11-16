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

    Turmite(Position newCurrentPosition, int newState, ArrayList<Pattern> newPatterns) {
        currentPosition = newCurrentPosition;
        direction = 1;
        state = newState;
        patterns = newPatterns;
    }

    Turmite() {
    }

    void move(Grid game) {
        for (Pattern p : patterns) {
            if (state == p.currentAntState && game.grid[currentPosition.x][currentPosition.y] == p.currentCellState) {
                state = p.newAntState;
                game.grid[currentPosition.x][currentPosition.y] = p.newCellState;
                changeDirection(p.direction);
                Position oldPosition = currentPosition;
                switch (direction) {
                    case 1:
                        if (currentPosition.x < game.row) {
                            currentPosition.x++;
                        }
                    case 2:
                        if (currentPosition.y < game.column) {
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
                if (checkObstacle(game))
                    currentPosition = oldPosition;
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

    public boolean checkObstacle(Grid game) {
        return game.grid[currentPosition.x][currentPosition.y] == 3;
    }

    void load() throws IOException {

        JOptionPane.showMessageDialog(null, "File loaded");
    }
}
