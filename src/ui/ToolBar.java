package ui;

import mode.*;
import shape.*;
import shape.Rectangle;
import ui.icon.IconFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToolBar extends JPanel {
    private static final Color ACTIVE_BG = new Color(180, 180, 180);
    private static final Color INACTIVE_BG = Color.WHITE;

    private static final int ROWS = 6;
    private static final int COLS = 1;
    private static final int HGAP = 10;
    private static final int VGAP = 10;

    private final Canvas canvas;
    private JButton currentBtn;
    private JButton lastModeBtn;

    public ToolBar(Canvas canvas) {
        this.canvas = canvas;
        setLayout(new GridLayout(ROWS, COLS, HGAP, VGAP));
        setBackground(INACTIVE_BG);

        add(createModeButton(IconFactory.createSelectIcon(), "Select",
                () -> canvas.setCurrentMode(new SelectMode(canvas))));

        add(createModeButton(IconFactory.createAssociationIcon(), "Association",
                () -> canvas.setCurrentMode(new LinkMode(canvas, AssociationLine::new))));

        add(createModeButton(IconFactory.createGeneralizationIcon(), "Generalization",
                () -> canvas.setCurrentMode(new LinkMode(canvas, GeneralizationLine::new))));

        add(createModeButton(IconFactory.createCompositionIcon(), "Composition",
                () -> canvas.setCurrentMode(new LinkMode(canvas, CompositionLine::new))));

        add(createDragBtn(IconFactory.createRectIcon(), "Rectangle", Rectangle::new));
        add(createDragBtn(IconFactory.createOvalIcon(), "Oval", Oval::new));

        this.lastModeBtn = (JButton) getComponent(0);
        reset();
    }

    private JButton createModeButton(Icon icon, String tip, Runnable onSelect) {
        JButton btn = createBaseBtn(icon, tip);
        btn.addActionListener(e -> {
            lastModeBtn = btn;
            highlight(btn);
            onSelect.run();
        });
        return btn;
    }

    private JButton createDragBtn(Icon icon, String tip, CreateMode.ShapeCreator creator) {
        JButton btn = createBaseBtn(icon, tip);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                highlight(btn);
                canvas.setCurrentMode(new CreateMode(canvas, creator, () -> reset()));
            }
            @Override public void mouseReleased(MouseEvent e) { forward(e); }
        });
        btn.addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseDragged(MouseEvent e) { forward(e); }
        });
        return btn;
    }

    private void forward(MouseEvent e) {
        Component src = (Component) e.getSource();
        Point p = SwingUtilities.convertPoint(src, e.getPoint(), canvas);
        canvas.dispatchEvent(new MouseEvent(canvas, e.getID(), e.getWhen(),
                e.getModifiersEx(), p.x, p.y, e.getClickCount(), e.isPopupTrigger()));
    }

    private JButton createBaseBtn(Icon icon, String tip) {
        JButton btn = new JButton(icon);
        btn.setToolTipText(tip);
        btn.setBackground(INACTIVE_BG);

        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusable(false);

        return btn;
    }

    private void highlight(JButton btn) {
        if (currentBtn != null) currentBtn.setBackground(INACTIVE_BG);
        currentBtn = btn;
        currentBtn.setBackground(ACTIVE_BG);
    }

    private void reset() {
        if (lastModeBtn == null) return;
        highlight(lastModeBtn);

        for (java.awt.event.ActionListener al : lastModeBtn.getActionListeners()) {
            al.actionPerformed(new java.awt.event.ActionEvent(lastModeBtn, 0, null));
        }
    }
}