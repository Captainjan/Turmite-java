package turmite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewTurmiteActionListener implements ActionListener {
    Game game;
    NewTurmiteActionListener(Game input){
        game = input;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = game.addTurmiteArea.getText();
        ArrayList<Pattern> newPatterns = new ArrayList<>();
        String[] lines = text.split("\\R");
        for (String line : lines) {
            String[] parts = line.split("-");
            if (parts.length == 5) {
                Pattern newPattern = new Pattern();
                newPattern.currentAntState = Integer.parseInt(parts[0]);
                newPattern.currentCellState = Integer.parseInt(parts[1]);
                newPattern.direction = parts[2].charAt(0);
                newPattern.newCellState = Integer.parseInt(parts[3]);
                newPattern.newAntState = Integer.parseInt(parts[4]);
                newPatterns.add(newPattern);
            }
        }
        Position startPos = new Position(40, 40);
        game.turmiteList.add(new Turmite(startPos, 0, newPatterns, game));
    }

}
