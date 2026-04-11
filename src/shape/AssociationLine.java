package shape;

import java.awt.*;

public class AssociationLine extends Line {
    private static final int ARROW_SIZE = 10;
    private static final double ARROW_ANGLE = Math.PI / 6;

    public AssociationLine(Port start, Port end) {
        super(start, end);
    }

    @Override
    protected void drawArrow(Graphics2D g2d, Point p1, Point p2) {
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        int x1 = (int) (p2.x - ARROW_SIZE * Math.cos(angle - ARROW_ANGLE));
        int y1 = (int) (p2.y - ARROW_SIZE * Math.sin(angle - ARROW_ANGLE));
        int x2 = (int) (p2.x - ARROW_SIZE * Math.cos(angle + ARROW_ANGLE));
        int y2 = (int) (p2.y - ARROW_SIZE * Math.sin(angle + ARROW_ANGLE));

        g2d.drawLine(p2.x, p2.y, x1, y1);
        g2d.drawLine(p2.x, p2.y, x2, y2);
    }
}