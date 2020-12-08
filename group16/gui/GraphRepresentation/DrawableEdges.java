package group16.gui.GraphRepresentation;

import java.awt.*;
import java.util.ArrayList;

public class DrawableEdges {
    private final ArrayList<DrawableEdge> edges = new ArrayList<>();

    public void draw(Graphics2D g) {
        for (DrawableEdge edge : edges) {
            edge.draw(g);
        }
    }

    public void add(DrawableEdge drawableEdge) {
        edges.add(drawableEdge);
    }

    public DrawableEdges copyEdges() {
        DrawableEdges copy = new DrawableEdges();
        for (DrawableEdge edge : edges) {
            DrawableEdge copyEdge = edge.copyEdge();
            copy.add(copyEdge);
        }
        return copy;
    }
}
