package mode;

import shape.Line;
import shape.Port;
import ui.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.BiFunction;

public class LinkMode extends Mode {
    private final Canvas canvas;
    private final BiFunction<Port, Port, Line> lineFactory;
    private Port startPort;
    private Point currentPoint;

    public LinkMode(Canvas canvas, BiFunction<Port, Port, Line> lineFactory) {
        this.canvas = canvas;
        this.lineFactory = lineFactory;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPort = canvas.findPortAt(e.getX(), e.getY());
        if (startPort != null) {
            currentPoint = e.getPoint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (startPort != null) {
            currentPoint = e.getPoint();
            canvas.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (startPort == null) return;

        Port endPort = canvas.findPortAt(e.getX(), e.getY());

        if (endPort != null && endPort.getParent() != startPort.getParent()) {
            canvas.addShape(lineFactory.apply(startPort, endPort));
        }

        startPort = null;
        currentPoint = null;
        canvas.repaint();
    }

    @Override
    public void drawOverlay(Graphics2D g2d) {
        if (startPort != null && currentPoint != null) {
            Point p1 = startPort.getAbsoluteLocation();

            g2d.setColor(Color.GRAY);
            float[] dash = {5.0f};
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));

            g2d.drawLine(p1.x, p1.y, currentPoint.x, currentPoint.y);

            g2d.setStroke(new BasicStroke(1));
        }
    }
}