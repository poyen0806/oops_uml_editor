package ui;

import mode.CreateObject;
import mode.SelectObject;
import shape.Oval;
import shape.Rectangle;
import ui.icon.IconFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToolBar extends JPanel {
    private static final Color ACTIVE_BG = new Color(180, 180, 180);
    private static final Color INACTIVE_BG = Color.WHITE;

    private static final int GRID_ROWS = 6;
    private static final int GRID_COLS = 1;
    private static final int GRID_GAP = 10;

    private static final int PAD_TOP = 10;
    private static final int PAD_LEFT = 5;
    private static final int PAD_BOTTOM = 10;
    private static final int PAD_RIGHT = 5;

    private final Canvas canvas;
    private JButton currentBtn;
    private JButton lastModeBtn;

    public ToolBar(Canvas canvas) {
        this.canvas = canvas;

        setLayout(new GridLayout(GRID_ROWS, GRID_COLS, GRID_GAP, GRID_GAP));
        setBorder(BorderFactory.createEmptyBorder(PAD_TOP, PAD_LEFT, PAD_BOTTOM, PAD_RIGHT));
        setBackground(INACTIVE_BG);

        add(createModeButton(IconFactory.createSelectIcon(), "Select"));
        add(createModeButton(IconFactory.createAssociationIcon(), "Association"));
        add(createModeButton(IconFactory.createGeneralizationIcon(), "Generalization"));
        add(createModeButton(IconFactory.createCompositionIcon(), "Composition"));

        add(createDragBtn(IconFactory.createRectIcon(), "Rectangle", Rectangle::new));
        add(createDragBtn(IconFactory.createOvalIcon(), "Oval", Oval::new));

        this.lastModeBtn = (JButton) getComponent(0);
        reset();
    }

    private JButton createModeButton(Icon icon, String tip) {
        JButton btn = createBaseBtn(icon, tip);
        btn.addActionListener(e -> {
            lastModeBtn = btn;
            highlight(btn);
            canvas.setCurrentMode(new SelectObject());
        });
        return btn;
    }

    private JButton createDragBtn(Icon icon, String tip, CreateObject.ShapeCreator creator) {
        JButton btn = createBaseBtn(icon, tip);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                highlight(btn);
                canvas.setCurrentMode(new CreateObject(canvas, creator, () -> reset()));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                forward(e);
            }
        });
        btn.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                forward(e);
            }
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
        btn.setOpaque(true);
        btn.setBackground(INACTIVE_BG);
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
        highlight(lastModeBtn);
        canvas.setCurrentMode(new SelectObject());
    }
}