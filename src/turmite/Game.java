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
import java.util.concurrent.CountDownLatch;

public class Game extends JFrame {
    Grid gameGrid = new Grid(80, 80);
    ArrayList<Turmite> turmiteList = new ArrayList<>();

    boolean stopped = false;

    JTextArea addTurmiteArea = new JTextArea(10, 10);

    JPanel gridPanel = new JPanel(new GridBagLayout());

    CountDownLatch latch = new CountDownLatch(1);

    Game() {
        super("Turmite+");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        addGridComponents(gridPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        addButtonComponents(buttonPanel);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(gridPanel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(buttonPanel, constraints);

        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private void addGridComponents(JPanel gridPanel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        for (int row = 0; row < gameGrid.row; row++) {
            for (int col = 0; col < gameGrid.column; col++) {
                JButton gridButton = new JButton();
                gridButton.setPreferredSize(new Dimension(10, 10));
                constraints.gridx = col;
                constraints.gridy = row;
                gridButton.setBackground(Color.BLACK);
                gridButton.setOpaque(true);
                gridPanel.add(gridButton, constraints);
            }
        }
    }

    private void addButtonComponents(JPanel buttonPanel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;


        JButton loadButton = new JButton("Load save");
        loadButton.addActionListener(new LoadActionListener());
        buttonPanel.add(loadButton, constraints);
        constraints.gridy++;

        JButton newGameButton = new JButton("New game");
        newGameButton.addActionListener(new NewGameActionListener());
        buttonPanel.add(newGameButton, constraints);
        constraints.gridy++;

        JButton addTurmiteButton = new JButton("Add turmite");
        addTurmiteButton.addActionListener(new NewTurmiteActionListener(this));
        buttonPanel.add(addTurmiteButton, constraints);
        constraints.gridy++;

        buttonPanel.add(addTurmiteArea, constraints);
        constraints.gridy++;

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new stopButtonActionListener());
        buttonPanel.add(stopButton, constraints);
        constraints.gridy++;

    }

    public void changeButtonAtPosition(JPanel gridPanel, Position p) {
        Component[] components = gridPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                GridBagConstraints gbc = ((GridBagLayout) gridPanel.getLayout()).getConstraints(button);
                if (gbc.gridx == p.y && gbc.gridy == p.x) {
                    if (button.getBackground() == Color.BLACK && gameGrid.grid[p.x][p.y] == 1) {
                        button.setBackground(Color.WHITE);
                    } else if (button.getBackground() == Color.WHITE && gameGrid.grid[p.x][p.y] == 0) {
                        button.setBackground(Color.BLACK);
                    }
                }
            }
        }
    }

    final class LoadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadTurmites();
            loadGrid();
        }
    }

    final class NewGameActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            newGame();
        }
    }

    final class stopButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stopped = !stopped;
        }
    }

    private void newGame() {
        gameGrid = new Grid(80, 80);
    }

    private void loadTurmites() {
        ArrayList<String> lines = new ArrayList<>();

        BufferedReader br;
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
            if (l.equals(lines.get(0)) && parts.length == 4) {
                Position p = new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                thomas.state = Integer.parseInt(parts[3]);
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

        BufferedReader br;
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
        Grid newGrid = new Grid(80, 80);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length == 50) {
                for (int j = 0; j < parts.length; j++) {
                    newGrid.grid[i][j] = Integer.parseInt(parts[j]);
                }
            }
        }
    }
}
