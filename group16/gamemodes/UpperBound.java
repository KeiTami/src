package group16.gamemodes;

import group16.algorithms.Greedy;
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

public class UpperBound extends JComponent {
    private final DrawableGraph drawableGraph;

    public UpperBound(DrawableGraph drawableGraph, JLabel countLabel) {
        this.drawableGraph = drawableGraph;
        addMouseListener(new ColorMouseListener(drawableGraph, countLabel));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (drawableGraph != null) {
            drawableGraph.draw(g2);
        }
    }

    private void isWin(int computerUpperBound, int playerUpperBound) {
        String messageToDisplay = "You got " + playerUpperBound + " colors.\n";
        if (playerUpperBound > computerUpperBound) {
            messageToDisplay += "This is sadly more than the computer calculated upper bound(" + computerUpperBound + ").";
        } else {
            messageToDisplay += "Congrats, you did it!(Computer generated upperbound: " + computerUpperBound + ").";
        }
        messageToDisplay += "\nYour remaining time: " + guiFrame.bestUpperboundTimeLabel.getText();
        guiFrame.stopGameMode(guiFrame.bestUpperboundPanel, guiFrame.bestUpperboundGraphPanel);
        JOptionPane.showMessageDialog(null, messageToDisplay);
    }

    private class ColorMouseListener extends MouseAdapter {

        private final DrawableGraph drawableGraph;
        private final JLabel countLabel;
        private final ArrayList<Color> coloredVertices = GUIUtil.fillWithNull();

        public ColorMouseListener(DrawableGraph drawableGraph, JLabel countLabel) {
            this.drawableGraph = drawableGraph;
            this.countLabel = countLabel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int playerUpperBound;
            DrawableVertices drawableVertices = drawableGraph.getDrawableVertices();
            DrawableVertex clickedVertex = drawableVertices.getClickedVertex(x, y);
            if (clickedVertex != null) {
                GUIUtil.chooseColor(drawableGraph, drawableVertices, clickedVertex, coloredVertices);
                playerUpperBound = drawableVertices.countColors();
                countLabel.setText("" + playerUpperBound);
                repaint();
                if (GUIUtil.areAllVerticesColored(coloredVertices)) {
                    int computerUpperBound = Greedy.countColours(GUI.graph.connections);
                    isWin(computerUpperBound, playerUpperBound);
                }
            }
        }
    }
}
