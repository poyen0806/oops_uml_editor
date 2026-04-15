package mode;

import shape.Shape;
import ui.component.Canvas;
import java.awt.event.MouseEvent;

/**
 * Concrete Strategy for creating new shapes via drag-and-drop.
 * Utilizes the Factory Pattern (via ShapeCreator) and a Callback mechanism
 * to completely decouple UI state management from shape instantiation logic.
 */
public class CreateMode extends Mode {

    /**
     * Functional interface acting as a lightweight Factory.
     * Allows the ToolBar to pass constructor references (e.g., Rectangle::new)
     * without knowing the specific implementation details of the shape.
     */
    @FunctionalInterface
    public interface ShapeCreator {
        Shape create(int x, int y);
    }

    private final Canvas canvas;
    private final ShapeCreator creator;
    private final Runnable onComplete; // Callback function to revert ToolBar/Canvas state

    public CreateMode(Canvas canvas, ShapeCreator creator, Runnable onComplete) {
        this.canvas = canvas;
        this.creator = creator;
        this.onComplete = onComplete;
    }

    /**
     * Executes the shape creation sequence.
     * Triggered when the user finishes dragging the button onto the Canvas.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.addShape(creator.create(e.getX(), e.getY()));

        if (onComplete != null) onComplete.run();
    }
}