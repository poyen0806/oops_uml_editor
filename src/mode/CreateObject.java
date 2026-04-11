package mode;

import shape.BasicObject;
import shape.ShapeFactory;
import ui.Canvas;
import java.awt.event.MouseEvent;

public class CreateObject extends Mode {
    private final Canvas canvas;
    private final Runnable onCompleteCallback;
    private final ShapeFactory factory;
    private int startX, startY;

    public CreateObject(Canvas canvas, ShapeFactory factory, Runnable onCompleteCallback) {
        this.canvas = canvas;
        this.factory = factory;
        this.onCompleteCallback = onCompleteCallback;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int endX = e.getX();
        int endY = e.getY();

        // 防呆：沒有拖曳就不產生形狀
        if (startX == endX && startY == endY) {
            if (onCompleteCallback != null) onCompleteCallback.run();
            return;
        }

        BasicObject obj = factory.create(startX, startY, endX, endY);
        canvas.addShape(obj);

        if (onCompleteCallback != null) {
            onCompleteCallback.run();
        }
    }
}