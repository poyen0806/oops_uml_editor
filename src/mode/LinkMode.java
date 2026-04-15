package mode;

import shape.link.Line;
import shape.Port;
import ui.component.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.BiFunction;

/**
 * Concrete Strategy for establishing connections (Links) between two Objects.
 * Employs the Factory Method pattern (via a functional interface) to decouple
 * the interaction logic from the specific UML line type being created.
 */
public class LinkMode extends Mode {
    private final Canvas canvas;

    // Functional Factory: Takes two Ports (start and end) and produces a Line.
    // This allows LinkMode to handle Association, Composition, and Generalization uniformly.
    private final BiFunction<Port, Port, Line> lineFactory;

    // State machine variables tracking the connection process
    private Port startPort;
    private Point currentPoint;

    public LinkMode(Canvas canvas, BiFunction<Port, Port, Line> lineFactory) {
        this.canvas = canvas;
        this.lineFactory = lineFactory;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // The connection sequence only begins if the user clicks precisely on a Port.
        startPort = canvas.findPortAt(e.getX(), e.getY());
        if (startPort != null) {
            currentPoint = e.getPoint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Updates the dynamic endpoint for the ephemeral rubber-band line rendering
        if (startPort != null) {
            currentPoint = e.getPoint();
            canvas.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (startPort == null) return;

        // Check if the drag ended on a valid target Port
        Port endPort = canvas.findPortAt(e.getX(), e.getY());

        // Domain Rule Validation:
        // 1. Must drop on a Port.
        // 2. Prevents self-linking (an object cannot connect to itself).
        if (endPort != null && endPort.getParent() != startPort.getParent()) {
            // Instantiate the specific Line type via the injected factory and commit it to the Canvas
            canvas.addShape(lineFactory.apply(startPort, endPort));
        }

        // Reset the connection sequence
        startPort = null;
        currentPoint = null;
        canvas.repaint();
    }

    @Override
    public void drawOverlay(Graphics2D g2d) {
        // Ephemeral Rendering: Draws a dynamic dashed line to provide real-time
        // visual feedback while the user is dragging between ports.
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