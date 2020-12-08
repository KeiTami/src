package group16.gui.GamemodeHints;

import group16.algorithms.Backtracking;
import group16.gui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExactHint implements ActionListener {

    private int hintsUsed = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        hintsUsed++;
        if (hintsUsed <= 3) {
            int computerColorCount = Backtracking.countColours(GUI.graph.connections, 0);
            JOptionPane.showMessageDialog(null, "The exact chromatic number of this graph is: " + computerColorCount);
        } else {

        }
    }
}
