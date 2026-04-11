package shape;

import java.awt.*;

public abstract class Line extends Shape {
    protected final Port startPort;
    protected final Port endPort;

    public Line(Port start, Port end) {
        this.startPort = start;
        this.endPort = end;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Point p1 = startPort.getAbsoluteLocation();
        Point p2 = endPort.getAbsoluteLocation();
        g2d.setColor(Color.BLACK);
        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        drawArrow(g2d, p1, p2);
    }

    protected abstract void drawArrow(Graphics2D g2d, Point p1, Point p2);
}