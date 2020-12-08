package group16.gui.GraphRepresentation;

import java.awt.*;

public class DrawableEdge {
    private final int startX;
    private final int endX;
    private final int startY;
    private final int endY;

    public DrawableEdge(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        g.drawLine(startX, startY, endX, endY);
    }

    public DrawableEdge copyEdge() {
        return new DrawableEdge(startX, startY, endX, endY);
    }
}
