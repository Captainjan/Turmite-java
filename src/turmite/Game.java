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
            loadTurmites();
            loadGrid();
            }
    }
    private void loadTurmites() {
        ArrayList<String> lines = new ArrayList<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("turmite.txt"));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) != null) break;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            lines.add(line);
        }

        try {
            br.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        for (String l : lines) {
            String[] parts = l.split(",");
            Turmite thomas = new Turmite();
            if (l == lines.get(0) && parts.length == 3) {
                Position p = new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                int state = Integer.parseInt(parts[3]);
                thomas.state = state;
                thomas.currentPosition = p;
            }
            if (parts.length == 5) {
                int inputCurrentAntState = Integer.parseInt(parts[0]);
                int inputCurrentCellState = Integer.parseInt(parts[1]);
                char inputDirection = parts[4].charAt(2);
                int inputNewAntState = Integer.parseInt(parts[3]);
                int inputNewCellState = Integer.parseInt(parts[4]);

                Pattern p = new Pattern(inputCurrentAntState, inputCurrentCellState, inputDirection, inputNewAntState, inputNewCellState);
                thomas.patterns.add(p);
            }
        }
    }
    private void loadGrid() {
        ArrayList<String> lines = new ArrayList<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("grid.txt"));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) != null) break;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            lines.add(line);
        }

        try {
            br.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Grid newGrid = new Grid(50,50);
        for (int i=0; i<lines.size();i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length == 50) {
                for(int j=0;j<parts.length;j++) {
                    newGrid.grid[i][j]=Integer.parseInt(parts[j]);
                }
            }
        }
    }
}
