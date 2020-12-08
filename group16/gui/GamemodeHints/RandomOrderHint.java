package group16.gui.GamemodeHints;

import group16.algorithms.Backtracking;
import group16.gamemodes.RandomOrder;
import group16.gui.GUI;
import group16.util.GUIUtil;
import group16.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RandomOrderHint implements ActionListener {

    public static int hintsUsed = 0;
    private boolean wasChromaticNumberShown = false;
    private final ArrayList<Color> forbiddenColors = new ArrayList<>();
    private final ArrayList<Color> colorsForChromaticNumber = new ArrayList<>();

    @Override
    public void actionPerformed(ActionEvent e) {
        hintsUsed++;
        String messageToPrint = "The forbidden colors for this vertex:\n";
        ArrayList<String> seenColors = new ArrayList<>();
        if (hintsUsed == 1 && !wasChromaticNumberShown) {
            int computerColorCount = Backtracking.countColours(GUI.graph.connections, 0);
            JOptionPane.showMessageDialog(null, "The exact chromatic number of this graph is: " + computerColorCount);
            wasChromaticNumberShown = true;
        } else if (hintsUsed < 5) {
            ArrayList<Integer> adjacentVertices = new ArrayList<>(GUI.graph.connections.get(RandomOrder.nextVertexIndex));
            for (Integer adjacentVertex : adjacentVertices) {
                Color colorToAdd = RandomOrder.coloredVertices.get(adjacentVertex);
                if (colorToAdd != null) {
                    String colorName = ColorUtils.getColorNameFromColor(colorToAdd);
                    if (!seenColors.contains(colorName)) {
                        seenColors.add(colorName);
                        messageToPrint += colorName + "\n";
                    }
                }
                forbiddenColors.add(colorToAdd);
            }
            if (isAllNull(forbiddenColors)) {
                messageToPrint = "There are no forbidden colors for this vertex.";
            }
            JOptionPane.showMessageDialog(null, messageToPrint);
        } else {
            ArrayList<Color> availableColors = new ArrayList<>();
            for (Color color : forbiddenColors) {
                if (!RandomOrder.coloredVertices.contains(color)) {
                    availableColors.add(color);
                }
            }
            Color colorToUse;
            if (!isAllNull(availableColors)) {
                colorToUse = availableColors.get(Util.random.nextInt(availableColors.size()));
            } else {
                float r = Util.random.nextFloat();
                float g = Util.random.nextFloat();
                float b = Util.random.nextFloat();
                colorToUse = new Color(r, g, b);
                while (forbiddenColors.contains(colorToUse)) {
                    r = Util.random.nextFloat();
                    g = Util.random.nextFloat();
                    b = Util.random.nextFloat();
                    colorToUse = new Color(r, g, b);
                }


            }
        }
    }

    private boolean isAllNull(ArrayList<Color> forbiddenColors) {
        for (Color color : forbiddenColors) {
            if (color != null) {
                return false;
            }
        }
        return true;
    }
}
