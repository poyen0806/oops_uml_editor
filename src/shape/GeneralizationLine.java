package shape;

import java.awt.*;

public class GeneralizationLine extends Line {
    private static final int TRIANGLE_SIZE = 12;

    public GeneralizationLine(Port start, Port end) {
        super(start, end);
    }

    @Override
    protected void drawArrow(Graphics2D g2d, Point p1, Point p2) {
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        // 計算三角形的三個頂點
        int[] xPoints = {
                p2.x,
                (int) (p2.x - TRIANGLE_SIZE * Math.cos(angle - Math.PI / 6)),
                (int) (p2.x - TRIANGLE_SIZE * Math.cos(angle + Math.PI / 6))
        };
        int[] yPoints = {
                p2.y,
                (int) (p2.y - TRIANGLE_SIZE * Math.sin(angle - Math.PI / 6)),
                (int) (p2.y - TRIANGLE_SIZE * Math.sin(angle + Math.PI / 6))
        };

        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, 3);
    }
}