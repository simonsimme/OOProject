package View.components;

import javax.swing.*;
import java.awt.*;

public class NotificationManager implements NotificationSystem {
    private final WindowManager windowManager;

    public NotificationManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    /**
     * Shows a notification message.
     *
     * @param message the notification message to display
     */
    @Override
    public void showNotification(String message) {
        JWindow window = new JWindow(windowManager.getFrame());
        window.setLayout(new BorderLayout());
        window.setSize(250, 100);
        window.setBackground(new Color(0, 0, 0, 0));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(45, 45, 45));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        int fontSize = 20;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        label.setFont(font);
        while (label.getPreferredSize().width > window.getWidth() - 20 && fontSize > 10) {
            fontSize--;
            font = new Font("Arial", Font.BOLD, fontSize);
            label.setFont(font);
        }
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.CENTER);

        window.add(panel);
        window.setAlwaysOnTop(true);

        int x = windowManager.getFrame().getX() + windowManager.getFrame().getWidth() - window.getWidth() - 10;
        int y = windowManager.getFrame().getY() + windowManager.getFrame().getHeight() - window.getHeight() - 10;
        window.setLocation(x, y);

        window.setVisible(true);

        Timer timer = new Timer(3000, e -> window.dispose());
        timer.setRepeats(false);
        timer.start();
    }
}
