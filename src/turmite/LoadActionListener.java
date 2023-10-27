package turmite;

import javax.swing.*;
import java.awt.event.*;


public class LoadActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e){
        JOptionPane.showMessageDialog(null,"File loaded");
    }
}
