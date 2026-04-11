package mode;

import shape.Shape;
import ui.Canvas;
import java.awt.event.MouseEvent;

public class CreateObject extends Mode {
    @FunctionalInterface
    public interface ShapeCreator {
        Shape create(int x, int y);
    }

    private final Canvas canvas;
    private final ShapeCreator creator;
    private final Runnable onComplete;

    public CreateObject(Canvas canvas, ShapeCreator creator, Runnable onComplete) {
        this.canvas = canvas;
        this.creator = creator;
        this.onComplete = onComplete;
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.addShape(creator.create(e.getX(), e.getY()));
        if (onComplete != null) onComplete.run();
    }
}