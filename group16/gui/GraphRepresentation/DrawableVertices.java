package group16.gui.GraphRepresentation;

import java.awt.*;
import java.util.ArrayList;

public class DrawableVertices {
    private final ArrayList<DrawableVertex> vertices = new ArrayList<>();

    public void draw(Graphics2D g) {
        for (DrawableVertex vertex : vertices) {
            vertex.draw(g);
        }
    }

    public int size() {
        return vertices.size();
    }

    public DrawableVertex get(int index) {
        return vertices.get(index);
    }

    public void add(DrawableVertex vertex) {
        vertices.add(vertex);
    }
    public int countColors() {
        int count = 0;
        ArrayList<Color> seenColors = new ArrayList<>();
        for (DrawableVertex vertex : vertices) {
            if (vertex.getColor() != Color.WHITE && !seenColors.contains(vertex.getColor())) {
                count++;
                seenColors.add(vertex.getColor());
            }
        }
        return count;
    }

    public int getIndex(DrawableVertex drawableVertex) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i) == drawableVertex) {
                return i;
            }
        }
        return -1;
    }

    public DrawableVertex getClickedVertex(int x, int y) {
        for (DrawableVertex vertex : vertices) {
            if (vertex.isInCircle(x, y)) {
                return vertex;
            }
        }
        return null;
    }

    public DrawableVertices copyVertices() {
        DrawableVertices copy = new DrawableVertices();
        for (DrawableVertex vertex : vertices) {
            DrawableVertex copyVertex = vertex.copyVertex();
            copy.add(copyVertex);
        }
        return copy;
    }

    public void selectVertex(int index, Graphics2D g) {
        vertices.get(index).selectVertex(g);
    }
}
