package ui.component;

import mode.*;
import shape.basic.Oval;
import shape.basic.Rectangle;
import shape.link.AssociationLine;
import shape.link.CompositionLine;
import shape.link.GeneralizationLine;
import ui.factory.IconFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The ToolBar component.
 * Acts as the control panel for switching between different modes.
 * It binds UI buttons to specific behaviors defined in the 'mode' package.
 */
public class ToolBar extends JPanel {
    // UI colors for button state
    private static final Color ACTIVE_BG = new Color(180, 180, 180);
    private static final Color INACTIVE_BG = Color.WHITE;

    // Grid layout for toolbar
    private static final int ROWS = 6;
    private static final int COLS = 1;
    private static final int HGAP = 10;
    private static final int VGAP = 10;

    private final Canvas canvas;
    private JButton currentBtn;  // Tracks the currently visually active button
    private JButton lastModeBtn; // Tracks the last sticky mode (Select/Link) to revert to after CreateMode

    public ToolBar(Canvas canvas) {
        this.canvas = canvas;
        setLayout(new GridLayout(ROWS, COLS, HGAP, VGAP));
        setBackground(INACTIVE_BG);

        // Sticky Modes (Click to activate)

        add(createModeButton(IconFactory.createSelectIcon(), "Select",
                () -> canvas.setCurrentMode(new SelectMode(canvas))));

        add(createModeButton(IconFactory.createAssociationIcon(), "Association",
                () -> canvas.setCurrentMode(new LinkMode(canvas, AssociationLine::new))));

        add(createModeButton(IconFactory.createGeneralizationIcon(), "Generalization",
                () -> canvas.setCurrentMode(new LinkMode(canvas, GeneralizationLine::new))));

        add(createModeButton(IconFactory.createCompositionIcon(), "Composition",
                () -> canvas.setCurrentMode(new LinkMode(canvas, CompositionLine::new))));

        // Drag-and-Drop Modes (Press & Drag to activate)
        add(createDragBtn(IconFactory.createRectIcon(), "Rectangle", Rectangle::new));
        add(createDragBtn(IconFactory.createOvalIcon(), "Oval", Oval::new));

        // Sets the default starting mode (Select)
        this.lastModeBtn = (JButton) getComponent(0);
        reset();
    }

    /**
     * Factory for sticky buttons (Select, Links).
     * These modes remain active until another button is clicked.
     */
    private JButton createModeButton(Icon icon, String tip, Runnable onSelect) {
        JButton btn = createBaseBtn(icon, tip);
        btn.addActionListener(e -> {
            lastModeBtn = btn; // Remembers this as a safe state to return to
            highlight(btn);
            onSelect.run();    // Executes the strategy injection
        });
        return btn;
    }

    /**
     * Factory for drag-and-drop shape creation buttons.
     * Intercepts mouse events on the button and forwards them to the Canvas.
     */
    private JButton createDragBtn(Icon icon, String tip, CreateMode.ShapeCreator creator) {
        JButton btn = createBaseBtn(icon, tip);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                highlight(btn);
                // Switches Canvas to CreateMode, passing the shape factory and a callback to revert the ToolBar
                canvas.setCurrentMode(new CreateMode(canvas, creator, () -> reset()));
            }
            @Override
            public void mouseReleased(MouseEvent e) { forward(e); }
        });

        btn.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { forward(e); }
        });
        return btn;
    }

    /**
     * Event Forwarding.
     * Translates coordinates from the Button's space to the Canvas's space
     * and artificially triggers a MouseEvent on the Canvas.
     * This allows dragging seamlessly from the toolbar into the drawing area.
     */
    private void forward(MouseEvent e) {
        Component src = (Component) e.getSource();
        Point p = SwingUtilities.convertPoint(src, e.getPoint(), canvas);
        canvas.dispatchEvent(new MouseEvent(canvas, e.getID(), e.getWhen(),
                e.getModifiersEx(), p.x, p.y, e.getClickCount(), e.isPopupTrigger()));
    }

    /**
     * Helper to standardize button appearance.
     */
    private JButton createBaseBtn(Icon icon, String tip) {
        JButton btn = new JButton(icon);
        btn.setToolTipText(tip);
        btn.setBackground(INACTIVE_BG);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusable(false);
        return btn;
    }

    /**
     * Visually highlights the active button.
     */
    private void highlight(JButton btn) {
        if (currentBtn != null) currentBtn.setBackground(INACTIVE_BG);
        currentBtn = btn;
        currentBtn.setBackground(ACTIVE_BG);
    }

    /**
     * Reverts the ToolBar and Canvas to the last known sticky mode (e.g., Select).
     * Typically called as a callback after a CreateMode finishes its drag-and-drop.
     */
    private void reset() {
        if (lastModeBtn == null) return;
        highlight(lastModeBtn);

        // Simulates a button click to ensure the Canvas mode is also updated
        for (java.awt.event.ActionListener al : lastModeBtn.getActionListeners()) {
            al.actionPerformed(new java.awt.event.ActionEvent(lastModeBtn, 0, null));
        }
    }
}