package turmite;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The main class that operates the game. Ties the logic and the graphics together. For simplicity it is also a JFrame
 */
public class Game extends JFrame {
    /**
     * The grid on which turmites move, it is always 80x80.
     */
    Grid gameGrid = new Grid(80, 80);
    /**
     * Stores the turmites that are moving on the grid
     */
    public ArrayList<Turmite> turmiteList = new ArrayList<>();
    /**
     * Indicates if the simulation is running or not
     */
    boolean stopped = false;
    /**
     * Textarea where the patterns for the turmite can be given.
     */
    JTextArea addTurmiteArea = new JTextArea(10, 10);
    /**
     * The panel where the graphical grid is.
     */
    JPanel gridPanel = new JPanel(new GridBagLayout());
    /**
     * The amount of time in milliseconds, the simulation waits after each turmite moving cycle.
     * Essentially simulation speed.
     */
    int speed;

    /**
     * Constructor for the Game class. Creates the graphical Frame, the panels with the needed orientation in the frame.
     */
    Game() {
        super("Turmite+");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        speed = 500;

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

    /**
     * Sets the graphical grid up with buttons and sets them to be black to indicate their state.
     * Black meaning the cell's state is 0.
     */
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
                gridButton.addActionListener(new GridClickActionListener(this.gameGrid));

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
        Menu.addActionListener(new ComboBoxActionListener(this, Menu));
        buttonPanel.add(Menu);
        constraints.gridy++;

        JButton addTurmiteButton = new JButton("Add turmite");
        addTurmiteButton.addActionListener(new NewTurmiteActionListener(this));
        buttonPanel.add(addTurmiteButton, constraints);
        constraints.gridy++;

        buttonPanel.add(addTurmiteArea, constraints);
        constraints.gridy++;

        JSlider animSpeed = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
        animSpeed.setMajorTickSpacing(250);
        animSpeed.setMinorTickSpacing(100);
        animSpeed.setPaintTicks(true);
        animSpeed.setPaintLabels(true);
        animSpeed.addChangeListener(new SliderListener(this));
        buttonPanel.add(animSpeed, constraints);
        constraints.gridy++;

        JButton stopButton = new JButton("Start/Stop");
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

    public void indicateTurmite(Position p) {
        Component[] components = gridPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                GridBagConstraints gbc = ((GridBagLayout) gridPanel.getLayout()).getConstraints(button);
                if (gbc.gridx == p.y && gbc.gridy == p.x) {
                    button.setBackground(Color.GREEN);
                }
            }
        }
    }

    public void newGame() {
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

    public void checkOverlap(Turmite input) {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = 0; i < turmiteList.size(); i++) {
            Turmite currentTurmite = turmiteList.get(i);
            if (currentTurmite != input && input.currentPosition == currentTurmite.currentPosition) {
                if (currentTurmite.state != input.state) {
                    if (currentTurmite.state == 0) {
                        toRemove.add(i);
                    } else {
                        toRemove.add(turmiteList.indexOf(input));
                    }
                } else {
                    currentTurmite.reverse();
                    input.reverse();
                }
            }
        }
        for(Integer i : toRemove){
            System.out.println(turmiteList.get(i).currentPosition + " Eaten" );
            turmiteList.remove((int)i);
        }
    }
}
