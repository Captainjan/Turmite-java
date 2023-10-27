package turmite;

import java.util.ArrayList;

public class Turmite {

    Position currentPosition;
    int direction;
    int state;
    ArrayList<Pattern> patterns=new ArrayList<>();
    Turmite(Position newCurrentPosition, int newState){
        currentPosition=newCurrentPosition;
        direction=1;
        state=newState;
    }
    void move(Grid game){
        for(Pattern p: patterns){
            if(state==p.currentAntState && game.grid[currentPosition.x][currentPosition.y] == p.currentCellState) {
                state=p.newAntState;
                game.grid[currentPosition.x][currentPosition.y]=p.newAntState;
            }
        }
    }
}
