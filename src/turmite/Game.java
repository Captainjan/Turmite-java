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
    boolean stopped = true;
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

    JButton stopButton = new JButton("Start/Stop");

    /**
     * Constructor for the Game class. Creates the graphical frame, the panels with the needed orientation in the frame.
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
     * Black meaning the cell's state is 0. It also adds the ActionListener for placing/removing obstacles.
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

    /**
     * Adds the Combobox with options for new game/saving/loading, adds the button for adding new turmites and a
     * start/stop button.
     */
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

        JSlider animSpeed = new JSlider(SwingConstants.HORIZONTAL, 0, 1000, 500);
        animSpeed.setMajorTickSpacing(250);
        animSpeed.setMinorTickSpacing(100);
        animSpeed.setPaintTicks(true);
        animSpeed.setPaintLabels(true);
        animSpeed.addChangeListener(new SliderListener(this));
        buttonPanel.add(animSpeed, constraints);
        constraints.gridy++;

        stopButton.setBackground(Color.RED);
        stopButton.addActionListener(new StopActionListener(this));
        buttonPanel.add(stopButton, constraints);
        constraints.gridy++;
    }

    /**
     * Changes the graphical cell at the given position to the colour it should be based on the logical grid.
     * White meaning the state is 1, Black meaning the state is 0 and Red meaning there is an obstacle which has a
     * value of 2. It gets the graphical position using the constraints of the GridBagLayout.
     */
    public void changeColorAtPosition(Position p) {
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

    /**
     * Inidicates a turmite at the given position with the colour green. It gets the graphical position using the
     * constraints of the GridBagLayout.
     */
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

    /**
     * Resets the logical and the graphical grid to the starting states. It does this by creating a new grid,
     * clearing the turmiteList, removing the gridPanel and readding it with the starting parameters.
     */
    public void newGame() {
        gameGrid = new Grid(80, 80);
        turmiteList.clear();
        stopped = true;
        this.remove(gridPanel);
        gridPanel.removeAll();
        addGridComponents();
        this.revalidate();
        this.repaint();
    }

    /**
     * Reloads the graphical grid. It does this by removing the gridPanel from the frame and then coloring
     * it based on the logical grid.
     */
    public void reloadGrid() {
        remove(gridPanel);
        gridPanel.removeAll();
        addGridComponents();

        Component[] components = gridPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                GridBagConstraints gbc = ((GridBagLayout) gridPanel.getLayout()).getConstraints(button);
                Position p = new Position(gbc.gridy, gbc.gridx);
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

    /**
     * Checks is the given turmite overlaps with any of the other turmites. If it does then two things may happen.
     * If the states are different then the index of the one with 0 gets added to an ArrayList. If their indexes are
     * the same then their direction gets reversed. After the for loop the turmites that had their indexes put in the
     * ArrayList get removed. The reason why they are not deleted in the main for loop is that when they get removed
     * they are shifted and this would cause problems while running the for loop.
     */
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
        for (Integer i : toRemove) {
            System.out.println(turmiteList.get(i).currentPosition + " Eaten");
            turmiteList.remove((int) i);
        }
    }

    /**
     * Creates a new turmite to be added to the TurmiteList. Splits the input of the Textarea at the line breaks
     * and then with by the dashes. Creates a new list of patterns but only creates the new turmite if the patterns
     * are valid. The new turmite will be spawned at the (40,40) position.
     */
    public void newTurmite(){
        if (stopped) {
            String text = addTurmiteArea.getText();
            System.out.println(text);
            ArrayList<Pattern> newPatterns = new ArrayList<>();
            String[] lines = text.split("\\R");
            boolean valid = true;
            for (String line : lines) {
                String[] parts = line.split("-");
                if (parts.length == 5) {
                    Pattern newPattern = new Pattern();
                    newPattern.currentAntState = Integer.parseInt(parts[0]);
                    newPattern.currentCellState = Integer.parseInt(parts[1]);
                    newPattern.direction = parts[2].charAt(0);
                    newPattern.newCellState = Integer.parseInt(parts[3]);
                    newPattern.newAntState = Integer.parseInt(parts[4]);
                    if(newPattern.checkPatternValidity()){
                        newPatterns.add(newPattern);
                    } else{
                        valid = false;
                    }
                } else{
                    valid = false;
                }
            }
            Position startPos = new Position(40, 40);
            if(valid){
                turmiteList.add(new Turmite(startPos, 0, newPatterns, this));
            }
        }
    }

    /**
     * Returns the color of the graphical cell at the given position. It finds the graphical cell's position by using
     * the GridBagLayout's constraints.
     */
    Color getColorAtPosition(Position p){
        Component[] components = gridPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                GridBagConstraints gbc = ((GridBagLayout) gridPanel.getLayout()).getConstraints(button);
                if (gbc.gridx == p.y && gbc.gridy == p.x) {
                    return button.getBackground();
                }
            }
        }
        return null;
    }

}
