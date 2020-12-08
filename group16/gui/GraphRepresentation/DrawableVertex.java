package group16.gui.GraphRepresentation;

import java.awt.*;

public class DrawableVertex {
    private final int x;
    private final int y;
    private final int size;
    private final int centerX;
    private final int centerY;
    private final int radius;
    private Color color = Color.white;

    public DrawableVertex(int x, int y, int size) {
        this.x = x;
        centerX = x + size / 2;
        this.y = y;
        centerY = y + size / 2;
        this.size = size;
        radius = size / 2;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        g.setStroke(new BasicStroke(2));
        g.drawOval(x, y, size, size);
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public boolean isInCircle(int x, int y) {
        double distanceSquared = (x - centerX) * (x - centerX) + (y - centerY) * (y - centerY);
        return distanceSquared <= radius * radius;
    }

    public Color getColor() {
        return color;
    }

    public DrawableVertex copyVertex() {
        DrawableVertex copy = new DrawableVertex(x, y, size);
        copy.setColor(color);
        return copy;
    }

    public void selectVertex(Graphics2D g) {
        g.setColor(Color.red);
        g.drawOval(x, y, size, size);
    }
}
