package mode;

import shape.BasicObject;
import shape.Port;
import shape.Shape;
import ui.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SelectMode extends Mode {
    private static final Color OVERLAY_FILL = new Color(0, 120, 215, 30);
    private static final Color OVERLAY_BORDER = new Color(0, 120, 215, 150);
    private static final float[] DASH_PATTERN = {5.0f};
    private static final BasicStroke DASH_STROKE = new BasicStroke(1,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, DASH_PATTERN, 0);
    private static final BasicStroke NORMAL_STROKE = new BasicStroke(1);

    private final Canvas canvas;
    private Point startPoint, lastPoint, currentPoint;
    private boolean isMoving = false;
    private Port resizingPort = null;
    private Point anchorPoint = null;

    public SelectMode(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = lastPoint = currentPoint = e.getPoint();
        resizingPort = canvas.findPortAt(e.getX(), e.getY());

        if (resizingPort != null) {
            BasicObject obj = resizingPort.getParent();
            Port.Direction dir = resizingPort.getDirection();

            int ax = dir.name().contains("W") ? obj.getX() + obj.getWidth() : obj.getX();
            int ay = dir.name().contains("N") ? obj.getY() + obj.getHeight() : obj.getY();
            anchorPoint = new Point(ax, ay);

            canvas.clearSelection();
            obj.setSelected(true);
        } else {
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
        }
        canvas.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentPoint = e.getPoint();
        if (resizingPort != null && anchorPoint != null) {
            resizingPort.getParent().resize(resizingPort.getDirection(), e.getX(), e.getY(), anchorPoint);
        } else if (isMoving && lastPoint != null) {
            int dx = e.getX() - lastPoint.x;
            int dy = e.getY() - lastPoint.y;
            for (Shape s : canvas.getShapes()) {
                if (s.isSelected()) s.move(dx, dy);
            }
            lastPoint = e.getPoint();
        }
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (resizingPort == null && !isMoving && startPoint != null && !startPoint.equals(e.getPoint())) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int w = Math.abs(startPoint.x - e.getX());
            int h = Math.abs(startPoint.y - e.getY());
            canvas.selectObjectsInArea(new java.awt.Rectangle(x, y, w, h));
        }

        // 狀態清理
        resizingPort = null;
        anchorPoint = null;
        isMoving = false;
        startPoint = lastPoint = currentPoint = null;
        canvas.repaint();
    }

    @Override
    public void drawOverlay(Graphics2D g2d) {
        // 僅在框選模式下繪製
        if (resizingPort == null && !isMoving && startPoint != null && currentPoint != null) {
            int x = Math.min(startPoint.x, currentPoint.x);
            int y = Math.min(startPoint.y, currentPoint.y);
            int w = Math.abs(startPoint.x - currentPoint.x);
            int h = Math.abs(startPoint.y - currentPoint.y);

            g2d.setStroke(DASH_STROKE);
            g2d.setColor(OVERLAY_BORDER);
            g2d.drawRect(x, y, w, h);
            g2d.setColor(OVERLAY_FILL);
            g2d.fillRect(x, y, w, h);
            g2d.setStroke(NORMAL_STROKE);
        }
    }
}