package ui.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * A custom Modal Dialog for editing BasicObject properties (Label Name and Fill Color).
 */
public class LabelDialog extends JDialog {

    // Centralized UI text and layout constants
    private static final String DIALOG_TITLE = "Customize Label Style";
    private static final String LABEL_NAME_TEXT = "Label Name:";
    private static final String LABEL_COLOR_TEXT = "Label Color:";
    private static final String PICK_BUTTON_TEXT = "Pick Color";
    private static final String OK_TEXT = "OK";
    private static final String CANCEL_TEXT = "Cancel";
    private static final String COLOR_CHOOSER_TITLE = "Select Fill Color";

    private static final int GAP = 10;
    private static final int HALF_GAP = GAP / 2;
    private static final int VGAP = 0;
    private static final int PADDING = 15;
    private static final int GRID_ROWS = 2;
    private static final int GRID_COLS = 2;

    // UI Components and State Variables
    private final JTextField nameField;
    private final JPanel colorPreview;
    private Color currentColor;

    // State flag used by the caller to verify if the user committed the changes
    private boolean confirmed = false;

    /**
     * Constructs the dialog and initializes it with the target object's current state.
     * The 'modal' flag (true) blocks interaction with the main window until this dialog is closed.
     */
    public LabelDialog(Frame parent, String name, Color color) {
        super(parent, DIALOG_TITLE, true);
        this.currentColor = color;

        setLayout(new BorderLayout(GAP, GAP));

        // Form Layout for Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, GAP, GAP));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // 1. Text Input Field
        inputPanel.add(new JLabel(LABEL_NAME_TEXT));
        nameField = new JTextField(name);
        inputPanel.add(nameField);

        // 2. Color Picker UI
        inputPanel.add(new JLabel(LABEL_COLOR_TEXT));
        colorPreview = new JPanel();
        colorPreview.setBackground(color);
        colorPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton pickBtn = new JButton(PICK_BUTTON_TEXT);

        // Triggers the native OS color chooser and updates the preview if a color is selected
        pickBtn.addActionListener(e -> {
            Color selected = JColorChooser.showDialog(this, COLOR_CHOOSER_TITLE, currentColor);
            if (selected != null) {
                currentColor = selected;
                colorPreview.setBackground(selected);
            }
        });

        JPanel colorBox = new JPanel(new BorderLayout(HALF_GAP, VGAP));
        colorBox.add(colorPreview, BorderLayout.CENTER);
        colorBox.add(pickBtn, BorderLayout.EAST);
        inputPanel.add(colorBox);

        add(inputPanel, BorderLayout.CENTER);

        // Action Buttons (OK / Cancel)
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton okBtn = new JButton(OK_TEXT);
        okBtn.addActionListener(e -> {
            confirmed = true;
            setVisible(false); // Yields control back to MainWindow
        });

        JButton cancelBtn = new JButton(CANCEL_TEXT);
        cancelBtn.addActionListener(e -> setVisible(false));

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Auto-sizes the dialog based on component preferred sizes
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() { return confirmed; }
    public String getLabelName() { return nameField.getText(); }
    public Color getLabelColor() { return currentColor; }
}