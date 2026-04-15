package mode;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The abstract base class for all user interaction behaviors.
 */
public abstract class Mode  {

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    /**
     * Renders temporary, mode-specific visual feedback on top of the Canvas.
     * Examples include a selection box or a dynamic line being dragged.
     */
    public void drawOverlay(Graphics2D g2d) {}
}