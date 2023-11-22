package turmite;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JFrame {
    Grid gameGrid = new Grid(80, 80);
    public ArrayList<Turmite> turmiteList = new ArrayList<>();

    boolean stopped = false;

    JTextArea addTurmiteArea = new JTextArea(10, 10);

    JPanel gridPanel = new JPanel(new GridBagLayout());

    Game() {
        super("Turmite+");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        addGridComponents();

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        addButtonComponents(buttonPanel);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(gridPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(buttonPanel, constraints);

        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    public void addGridComponents() {
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

        JComboBox<String> Menu = new JComboBox<>();
        Menu.addItem("New game");
        Menu.addItem("Load game");
        Menu.addItem("Save game");
        Menu.addActionListener(new ComboBoxActionListener(this,Menu));
        buttonPanel.add(Menu);
        constraints.gridy++;

        JButton addTurmiteButton = new JButton("Add turmite");
        addTurmiteButton.addActionListener(new NewTurmiteActionListener(this));
        buttonPanel.add(addTurmiteButton, constraints);
        constraints.gridy++;

        buttonPanel.add(addTurmiteArea, constraints);
        constraints.gridy++;

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new StopActionListener(this));
        buttonPanel.add(stopButton, constraints);
        constraints.gridy++;

    }

    public void changeButtonAtPosition(Position p) {
        Component[] components = gridPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                GridBagConstraints gbc = ((GridBagLayout) gridPanel.getLayout()).getConstraints(button);
                if (gbc.gridx == p.y && gbc.gridy == p.x) {
                    if (button.getBackground() != Color.WHITE && gameGrid.getAtPosition(p) == 1) {
                        button.setBackground(Color.WHITE);
                    } else if (button.getBackground() != Color.BLACK && gameGrid.getAtPosition(p) == 0) {
                        button.setBackground(Color.BLACK);
                    } else if (button.getBackground() != Color.RED && gameGrid.getAtPosition(p) == 2) {
                        button.setBackground(Color.RED);
                    }
                }
            }
        }
    }

    public void newGame() {
        System.out.println("In function");
        gameGrid = new Grid(80, 80);
        turmiteList.clear();
        stopped = false;
        this.remove(gridPanel);
        gridPanel.removeAll();
        addGridComponents();
        this.revalidate();
        this.repaint();
    }

    public void reloadGrid() {
        remove(gridPanel);
        gridPanel.removeAll();
        addGridComponents();

        Component[] components = gridPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                GridBagConstraints gbc = ((GridBagLayout) gridPanel.getLayout()).getConstraints(button);
                Position p = new Position(gbc.gridx, gbc.gridy);
                if (gameGrid.getAtPosition(p) == 1) {
                    button.setBackground(Color.WHITE);
                } else if (gameGrid.getAtPosition(p) == 0) {
                    button.setBackground(Color.BLACK);
                } else if (gameGrid.getAtPosition(p) == 2) {
                    button.setBackground(Color.RED);
                }
            }
        }
        revalidate();
        repaint();
    }

}
