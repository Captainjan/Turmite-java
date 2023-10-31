package turmite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game {
    Grid gameGrid;
    ArrayList<Turmite> turmiteList;

    Game() {
        JFrame frame = new JFrame("Turmite+");
        JPanel panel = new JPanel();

        JButton loadButton = new JButton("Load save");
        ActionListener loadButtonClick = new LoadActionListener();
        loadButton.addActionListener(loadButtonClick);

        JButton button2 = new JButton("New turmite");


        frame.add(loadButton, BorderLayout.WEST);
        frame.add(button2, BorderLayout.EAST);
        frame.add(panel, BorderLayout.NORTH);

        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    final class LoadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> lines = new ArrayList<>();
            Position startpos = new Position(50, 50);
            Turmite thomas = new Turmite(startpos, 0);

            BufferedReader br = new BufferedReader(new FileReader("turmite.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            try {
                br.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            for (String l : lines) {
                String[] parts = l.split(",");
                if (parts.length == 7) {
                    Position p= new Position()
                    Turmite thomas = new Turmite(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]);
                    int inputCurrentAntState = Integer.parseInt(parts[2]);
                    int inputCurrentCellState = Integer.parseInt(parts[3]);
                    char inputDirection = parts[4].charAt(0);
                    int inputNewAntState = Integer.parseInt(parts[5]);
                    int inputNewCellState = Integer.parseInt(parts[6]);

                    Pattern p = new Pattern(inputCurrentAntState, inputCurrentCellState, inputDirection, inputNewAntState, inputNewCellState);
                    thomas.patterns.add(p);
                }
            }
        }
    }
}
