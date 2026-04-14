package shape.link;

import shape.Port;

import java.awt.*;

public class CompositionLine extends Line {
    private static final int DIAMOND_SIZE = 12;
    private static final int VERTEX_COUNT = 4;

    public CompositionLine(Port start, Port end) {
        super(start, end);
    }

    @Override
    protected void drawArrow(Graphics2D g2d, Point p1, Point p2) {
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        int x2 = p2.x;
        int y2 = p2.y;

        int xMid = (int) (p2.x - DIAMOND_SIZE * Math.cos(angle));
        int yMid = (int) (p2.y - DIAMOND_SIZE * Math.sin(angle));

        int xLeft = (int) (xMid - (DIAMOND_SIZE / 2.0) * Math.cos(angle - Math.PI / 2));
        int yLeft = (int) (yMid - (DIAMOND_SIZE / 2.0) * Math.sin(angle - Math.PI / 2));

        int xRight = (int) (xMid - (DIAMOND_SIZE / 2.0) * Math.cos(angle + Math.PI / 2));
        int yRight = (int) (yMid - (DIAMOND_SIZE / 2.0) * Math.sin(angle + Math.PI / 2));

        int xTail = (int) (p2.x - 2 * DIAMOND_SIZE * Math.cos(angle));
        int yTail = (int) (p2.y - 2 * DIAMOND_SIZE * Math.sin(angle));

        int[] xPoints = {x2, xLeft, xTail, xRight};
        int[] yPoints = {y2, yLeft, yTail, yRight};

        // 繪製實心菱形
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(xPoints, yPoints, VERTEX_COUNT);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, VERTEX_COUNT);
    }
}