package group16.util;

import group16.gui.GUI;
import group16.gui.GraphRepresentation.DrawableGraph;
import group16.gui.GraphRepresentation.DrawableVertex;
import group16.gui.GraphRepresentation.DrawableVertices;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIUtil {
    public static JFrame createFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(400, 100, 1200, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.setVisible(true);
        return frame;
    }

    public static boolean areAllVerticesColored(ArrayList<Color> coloredVertices) {
        for (Color coloredVertex : coloredVertices) {
            if (coloredVertex == null) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Color> fillWithNull() {
        ArrayList<Color> coloredVertices = new ArrayList<>();
        for (int index = 0; index < GUI.graph.getVerticesCount(); index++) {
            coloredVertices.add(null);
        }
        return coloredVertices;
    }

    public static boolean isColorAvailable(DrawableGraph drawableGraph, Color color, DrawableVertex vertex) {
        int ourVertexIndex = drawableGraph.getDrawableVertices().getIndex(vertex);
        for (int i = 0; i < GUI.graph.connections.get(drawableGraph.getDrawableVertices().getIndex(vertex)).size(); i++) {
            if (drawableGraph.getDrawableVertices().get(GUI.graph.connections.get(ourVertexIndex).get(i)).getColor().getRGB() == color.getRGB()) {
                return false;
            }
        }
        return true;
    }

    public static void chooseColor(DrawableGraph drawableGraph, DrawableVertices drawableVertices, DrawableVertex clickedVertex, ArrayList<Color> usedColors) {
        Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
        try {
            if (GUIUtil.isColorAvailable(drawableGraph, newColor, clickedVertex)) {
                clickedVertex.setColor(newColor);
                usedColors.set(drawableVertices.getIndex(clickedVertex), newColor);
            } else {
                JOptionPane.showMessageDialog(null, "Color not available, try another color.");
            }
        } catch (NullPointerException ignored) {
        }
    }
}
