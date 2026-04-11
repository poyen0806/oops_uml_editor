package ui;

import mode.CreateObject;
import mode.Mode;
import mode.SelectObject;
import shape.Oval;
import shape.Rectangle;
import ui.icon.IconFactory;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel {

    private static final Color ACTIVE_BG_COLOR = new Color(180, 180, 180);
    private static final Color INACTIVE_BG_COLOR = Color.WHITE;

    private static final int ROWS = 6;
    private static final int COLS = 1;
    private static final int HGAP = 10;
    private static final int VGAP = 10;

    private static final int PADDING_TOP = 10;
    private static final int PADDING_LEFT = 5;
    private static final int PADDING_BOTTOM = 10;
    private static final int PADDING_RIGHT = 5;

    private final Canvas canvas;
    private final JButton selectBtn;
    private JButton currentlyHighlightedBtn;

    public ToolBar(Canvas canvas) {
        this.canvas = canvas;

        setLayout(new GridLayout(ROWS, COLS, HGAP, VGAP));
        setBorder(BorderFactory.createEmptyBorder(
                PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT
        ));

        selectBtn = createButton(IconFactory.createSelectIcon(), "Select");
        JButton assocBtn = createButton(IconFactory.createAssociationIcon(), "Association");
        JButton genBtn = createButton(IconFactory.createGeneralizationIcon(), "Generalization");
        JButton compBtn = createButton(IconFactory.createCompositionIcon(), "Composition");
        JButton rectBtn = createButton(IconFactory.createRectIcon(), "Rect");
        JButton ovalBtn = createButton(IconFactory.createOvalIcon(), "Oval");

        setHighlightedButton(selectBtn);
        canvas.setCurrentMode(new SelectObject());

        bindActions(selectBtn, assocBtn, genBtn, compBtn, rectBtn, ovalBtn);
    }

    private void bindActions(JButton selectBtn, JButton assocBtn, JButton genBtn,
                             JButton compBtn, JButton rectBtn, JButton ovalBtn) {

        ActionListener stickyListener = e -> {
            JButton clickedBtn = (JButton) e.getSource();
            Mode newMode = new SelectObject(); // 之後可根據按鈕注入不同 Mode
            setHighlightedButton(clickedBtn);
            canvas.setCurrentMode(newMode);
        };

        selectBtn.addActionListener(stickyListener);
        assocBtn.addActionListener(stickyListener);
        genBtn.addActionListener(stickyListener);
        compBtn.addActionListener(stickyListener);

        rectBtn.addActionListener(e -> {
            setHighlightedButton(rectBtn);
            canvas.setCurrentMode(new CreateObject(canvas, Rectangle::new, this::forceResetToSelectMode));
        });

        ovalBtn.addActionListener(e -> {
            setHighlightedButton(ovalBtn);
            canvas.setCurrentMode(new CreateObject(canvas, Oval::new, this::forceResetToSelectMode));
        });
    }

    private JButton createButton(Icon icon, String tooltip) {
        JButton btn = new JButton(icon);
        btn.setToolTipText(tooltip);

        btn.setOpaque(true);
        btn.setContentAreaFilled(true);

        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFocusable(false);

        btn.setBackground(INACTIVE_BG_COLOR);

        add(btn);
        return btn;
    }

    private void setHighlightedButton(JButton btn) {
        if (currentlyHighlightedBtn != null) {
            currentlyHighlightedBtn.setBackground(INACTIVE_BG_COLOR);
        }

        currentlyHighlightedBtn = btn;
        currentlyHighlightedBtn.setBackground(ACTIVE_BG_COLOR);
    }

    public void forceResetToSelectMode() {
        setHighlightedButton(selectBtn);
        canvas.setCurrentMode(new SelectObject());
    }
}