package mode;

import shape.Shape;
import ui.component.Canvas;
import java.awt.event.MouseEvent;

public class CreateMode extends Mode {
    @FunctionalInterface
    public interface ShapeCreator {
        Shape create(int x, int y);
    }

    private final Canvas canvas;
    private final ShapeCreator creator;
    private final Runnable onComplete;

    public CreateMode(Canvas canvas, ShapeCreator creator, Runnable onComplete) {
        this.canvas = canvas;
        this.creator = creator;
        this.onComplete = onComplete;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.addShape(creator.create(e.getX(), e.getY()));
        if (onComplete != null) onComplete.run();
    }
}