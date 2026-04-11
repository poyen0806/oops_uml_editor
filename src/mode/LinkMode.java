package mode;

import shape.Line;
import shape.Port;
import ui.Canvas;

import java.awt.event.MouseEvent;
import java.util.function.BiFunction;

public class LinkMode extends Mode {
    private final Canvas canvas;
    private final BiFunction<Port, Port, Line> lineFactory;
    private Port startPort;

    public LinkMode(Canvas canvas, BiFunction<Port, Port, Line> lineFactory) {
        this.canvas = canvas;
        this.lineFactory = lineFactory;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPort = canvas.findPortAt(e.getX(), e.getY());
    }

    // 維持空
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (startPort == null) return;

        Port endPort = canvas.findPortAt(e.getX(), e.getY());

        if (endPort != null && endPort.getParent() != startPort.getParent()) {
            canvas.addShape(lineFactory.apply(startPort, endPort));
        }

        startPort = null;
        canvas.repaint();
    }
}