package main;

import ui.MainWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Application entry point.
 * Bootstraps the Oops UML Editor and initializes the primary user interface.
 */
public class Main {
    public static void main(String[] args) {

        // Ensures UI creation and updates are executed safely on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Applies the native OS look and feel for a consistent user experience
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Initializes the primary View component
            MainWindow window = new MainWindow();
            // Centers the window on the screen
            window.setLocationRelativeTo(null);
            // Displays the window
            window.setVisible(true);
        });
    }
}