package group16.gui.GraphRepresentation;

import java.awt.*;

import static group16.gui.GUI.graph;
import static group16.util.Util.random;

public class DrawableGraph {
    private DrawableEdges drawableEdges = new DrawableEdges();
    private DrawableVertices drawableVertices = new DrawableVertices();
    private final int verticesCount = graph.getVerticesCount();
    private final int size = 30;
    private final int minXPoint;
    private final int minYPoint;
    private final int height;
    private final int width;
    private Graphics2D g;

    public DrawableGraph(int height, int width) {
        this.height = height;
        this.width = width;
        minXPoint = width / verticesCount;
        minYPoint = height / verticesCount;
        addVertices();
        addEdges();
    }

    public void setGraph(DrawableVertices drawableVertices, DrawableEdges drawableEdges) {
        this.drawableEdges = drawableEdges;
        this.drawableVertices = drawableVertices;
    }

    public void draw(Graphics2D g) {
        this.g = g;
        drawableEdges.draw(g);
        drawableVertices.draw(g);
    }

    public void selectVertex(int index) {
        drawableVertices.selectVertex(index, g);
    }

    public void addVertices() {
        for (int i = 0; i < verticesCount; i++) {
            int xPos = random.nextInt(width - 2 * minXPoint);
            int yPos = random.nextInt(height - 2 * minYPoint);
            if (i > 0) {
                for (int j = 0; j < i; j++) {
                    int lastX = drawableVertices.get(j).getX();
                    int lastY = drawableVertices.get(j).getY();
                    while ((xPos > lastX && xPos < lastX + 2 * size) && (yPos > lastY && yPos < lastY + 2 * size)) {
                        xPos = random.nextInt(width - 2 * minXPoint);
                        yPos = random.nextInt(height - 2 * minYPoint);
                    }
                }
            }
            DrawableVertex vertex = new DrawableVertex(xPos, yPos, size);
            drawableVertices.add(vertex);
        }
    }

    public void addEdges() {
        for (int i = 0; i < graph.connections.size(); i++) {
            for (int j = 0; j < graph.connections.get(i).size(); j++) {
                int startX = drawableVertices.get(i).getCenterX();
                int startY = drawableVertices.get(i).getCenterY();
                int endX = drawableVertices.get(graph.connections.get(i).get(j)).getCenterX();
                int endY = drawableVertices.get(graph.connections.get(i).get(j)).getCenterY();
                DrawableEdge drawableEdge = new DrawableEdge(startX, startY, endX, endY);
                drawableEdges.add(drawableEdge);
            }
        }
    }

    public int size() {
        return drawableVertices.size();
    }

    public DrawableVertices getDrawableVertices() {
        return drawableVertices;
    }

    public DrawableGraph copyGraph() {
        DrawableGraph copy = new DrawableGraph(height, width);
        copy.setGraph(drawableVertices.copyVertices(), drawableEdges.copyEdges());
        return copy;
    }
}
