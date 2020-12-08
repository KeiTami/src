package group16.gamemodes;

import group16.algorithms.Backtracking;
import group16.gui.GUI;
import group16.gui.GamemodeHints.RandomOrderHint;
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
import static group16.util.Util.random;

public class RandomOrder extends JComponent {
    private final DrawableGraph drawableGraph;
    public static int nextVertexIndex;
    public static final ArrayList<Color> coloredVertices = GUIUtil.fillWithNull();
    private JLabel countLabel;
    private int playerColorCount;

    public RandomOrder(DrawableGraph drawableGraph, JLabel countLabel) {
        this.drawableGraph = drawableGraph;
        this.countLabel = countLabel;
        addMouseListener(new ColorMouseListener(drawableGraph, countLabel));
        nextVertexIndex = random.nextInt(drawableGraph.size());
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (drawableGraph != null) {
            drawableGraph.draw(g2);
            drawableGraph.selectVertex(nextVertexIndex);
            repaint();
        }
    }

    private void isWin(int computerColorCount, int playerColorCount) {
        String messageToDisplay = "You got " + playerColorCount + " colors.\n";
        messageToDisplay += "The computer generated count is: " + computerColorCount;
        guiFrame.stopGameMode(guiFrame.randomOrderPanel, guiFrame.randomOrderGraphPanel);
        JOptionPane.showMessageDialog(null, messageToDisplay);
    }

    public class ColorMouseListener extends MouseAdapter {

        private final DrawableGraph drawableGraph;
        private final JLabel countLabel;

        private ColorMouseListener(DrawableGraph drawableGraph, JLabel countLabel) {
            this.drawableGraph = drawableGraph;
            this.countLabel = countLabel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            DrawableVertices drawableVertices = drawableGraph.getDrawableVertices();
            DrawableVertex clickedVertex = drawableVertices.getClickedVertex(x, y);
            if (clickedVertex != null) {
                if (clickedVertex != drawableVertices.get(nextVertexIndex)) {
                    JOptionPane.showMessageDialog(null, "You have to color the highlighted vertex first.");
                } else {
                    GUIUtil.chooseColor(drawableGraph, drawableVertices, clickedVertex, coloredVertices);
                    playerColorCount = drawableVertices.countColors();
                    repaintGraph();
                    if (GUIUtil.areAllVerticesColored(coloredVertices)) {
                        int computerColorCount = Backtracking.countColours(GUI.graph.connections, 0);
                        isWin(computerColorCount, playerColorCount);
                    } else {
                        while (coloredVertices.get(nextVertexIndex) != null) {
                            nextVertexIndex = random.nextInt(drawableGraph.size());
                            RandomOrderHint.hintsUsed = 0;
                        }
                        drawableGraph.selectVertex(nextVertexIndex);
                        repaint();
                    }
                }
            }
        }
    }

    public void repaintGraph() {
        countLabel.setText("" + playerColorCount);
        repaint();
    }
}
