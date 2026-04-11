package mode;

import shape.Shape;
import ui.Canvas;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class SelectMode extends Mode {
    private final Canvas canvas;
    private Point startPoint;

    public SelectMode(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
        Shape target = canvas.findObjectAt(e.getX(), e.getY());

        canvas.clearSelection();
        if (target != null) {
            target.setSelected(true);
        }
        canvas.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        canvas.clearAllHover();

        Shape target = canvas.findObjectAt(e.getX(), e.getY());
        if (target != null) {
            target.setHovered(true);
        }
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (startPoint == null) return;

        if (!startPoint.equals(e.getPoint())) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            java.awt.Rectangle area = new java.awt.Rectangle(x, y, width, height);
            canvas.selectObjectsInArea(area);
        }
        startPoint = null;
    }
}