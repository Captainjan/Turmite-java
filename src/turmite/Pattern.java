package turmite;

public class Pattern {
    int currentAntState;
    int currentCellState;
    char direction;
    int newAntState;
    int newCellState;

    Pattern(int a, int b, char c, int d, int e){
        currentAntState=a;
        currentCellState=b;
        direction=c;
        newAntState=d;
        newCellState=e;
    }
}
