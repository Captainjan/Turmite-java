package turmite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Game extends JFrame implements Serializable {
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

                Position p = new Position(row, col);
                GridButton gridButton = new GridButton(p);
                gridButton.setPreferredSize(new Dimension(10, 10));
                gridButton.setBackground(Color.BLACK);
                gridButton.setOpaque(true);
                gridButton.addActionListener(new GridClickActionListener(this.gameGrid, gridButton));

                constraints.gridx = col;
                constraints.gridy = row;
                gridPanel.add(gridButton, constraints);
            }
        }
    }

    private void addButtonComponents(JPanel buttonPanel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;


        JButton loadButton = new JButton("Load save");
//        loadButton.addActionListener(new LoadActionListener());
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
                    if (button.getBackground() == Color.BLACK && gameGrid.getAtPosition(p) == 1) {
                        button.setBackground(Color.WHITE);
                    } else if (button.getBackground() == Color.WHITE && gameGrid.getAtPosition(p) == 0) {
                        button.setBackground(Color.BLACK);
                    }
                }
            }
        }
    }

//    final class LoadActionListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//        }
//    }

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
        turmiteList = new ArrayList<>();
        stopped = false;
        this.remove(gridPanel);
        gridPanel.removeAll();
        addGridComponents(gridPanel);

        this.revalidate();
        this.repaint();

        latch = new CountDownLatch(1);
    }

}
