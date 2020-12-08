package group16.gui.GamemodeHints;

import group16.algorithms.Backtracking;
import group16.gamemodes.RandomOrder;
import group16.gui.GUI;
import group16.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpperBoundHint implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int computerColorCount = Backtracking.countColours(GUI.graph.connections, 0);
        JOptionPane.showMessageDialog(null, "The exact chromatic number of this graph is: " + computerColorCount);
    }
}