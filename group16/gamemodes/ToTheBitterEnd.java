package group16.gamemodes;

import group16.algorithms.Backtracking;
import group16.gui.GUI;
import group16.gui.GraphRepresentation.DrawableGraph;
import group16.gui.GraphRepresentation.DrawableVertex;
import group16.gui.GraphRepresentation.DrawableVertices;
import group16.util.GUIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static group16.gui.GUI.guiFrame;

public class ToTheBitterEnd extends JComponent {
    private final DrawableGraph drawableGraph;

    public ToTheBitterEnd(DrawableGraph drawableGraph, JLabel countLabel){
        this.drawableGraph = drawableGraph;
        addMouseListener(new ColorMouseListener(drawableGraph, countLabel));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (drawableGraph != null) {
            drawableGraph.draw(g2);
        }
    }

    private void isWin(int computerColorCount, int playerColorCount) {
        if (playerColorCount == computerColorCount) {
            String messageToDisplay = "Good Job!\nYour time: " + guiFrame.toTheBitterEndTimeLabel.getText();
            guiFrame.stopGameMode(guiFrame.toTheBitterEndPanel, guiFrame.toTheBitterEndGraphPanel);
            JOptionPane.showMessageDialog(null, messageToDisplay);
        }
    }

    private class ColorMouseListener extends MouseAdapter {

        private final DrawableGraph drawableGraph;
        private final JLabel colorCountLabel;
        private final ArrayList<Color> coloredVertices = GUIUtil.fillWithNull();

        public ColorMouseListener (DrawableGraph drawableGraph, JLabel colorCountLabel) {
            this.drawableGraph = drawableGraph;
            this.colorCountLabel = colorCountLabel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int playerColorCount;
            DrawableVertices drawableVertices = drawableGraph.getDrawableVertices();
            DrawableVertex clickedVertex = drawableVertices.getClickedVertex(x, y);
            if (clickedVertex != null) {
                GUIUtil.chooseColor(drawableGraph, drawableVertices, clickedVertex, coloredVertices);
                playerColorCount = drawableVertices.countColors();
                colorCountLabel.setText("" + playerColorCount);
                repaint();
                if (GUIUtil.areAllVerticesColored(coloredVertices)) {
                    int computerColorCount = Backtracking.countColours(GUI.graph.connections, 0);
                    isWin(computerColorCount, playerColorCount);
                }
            }
        }
    }
}
