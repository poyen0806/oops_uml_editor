package mode;

import shape.Shape;
import ui.Canvas;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class SelectMode extends Mode {
    private final Canvas canvas;
    private Point startPoint;
    private Point lastPoint;
    private boolean isMoving = false;

    public SelectMode(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
        lastPoint = e.getPoint();

        Shape target = canvas.findObjectAt(e.getX(), e.getY());

        if (target != null) {
            if (!target.isSelected()) {
                canvas.clearSelection();
                target.setSelected(true);
            }
            isMoving = true;
        } else {
            canvas.clearSelection();
            isMoving = false;
        }
        canvas.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastPoint == null) return;

        if (isMoving) {
            int dx = e.getX() - lastPoint.x;
            int dy = e.getY() - lastPoint.y;

            for (Shape s : canvas.getShapes()) {
                if (s.isSelected()) {
                    s.move(dx, dy);
                }
            }

            lastPoint = e.getPoint();
            canvas.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (startPoint == null) return;

        if (!isMoving && !startPoint.equals(e.getPoint())) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            canvas.selectObjectsInArea(new java.awt.Rectangle(x, y, width, height));
        }

        startPoint = null;
        lastPoint = null;
        isMoving = false;
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
}