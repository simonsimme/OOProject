// WindowManager.java
package View.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class WindowManager {
    private final JFrame frame;

    public WindowManager() {
        frame = new JFrame("Concorde");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        setIconImage();
    }

    private void setIconImage() {
        //ImageIcon icon = new ImageIcon(getClass().getResource("./src/main/java/View/components/coding.png"));
       // frame.setIconImage(icon.getImage());
    }

    public void setContentPane(Container contentPane) {
        frame.setContentPane(contentPane);
        frame.revalidate();
        frame.repaint();
    }

    public void addWindowListener(WindowListener listener) {
        frame.addWindowListener(listener);
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    public void closeWindow() {
        frame.dispose();
    }

    public JFrame getFrame() {
        return frame;
    }
}