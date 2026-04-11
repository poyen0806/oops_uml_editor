package main;

import ui.MainWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // 設定系統原生外觀
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainWindow window = new MainWindow();
            window.setLocationRelativeTo(null); // 視窗置中顯示
            window.setVisible(true);
        });
    }
}