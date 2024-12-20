// WindowManager.java
package View.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

/**
 * The {@code WindowManager} class manages a JFrame window for the application. It provides methods to customize the window,
 * set its content pane, handle window events, and display or close the window.
 * <p>
 * This class is intended to encapsulate the behavior of the main application window, making it easy to set up, update, and
 * manage the window's lifecycle.
 * </p>
 */
public class WindowManager {
    private final JFrame frame;

    /**
     * Constructs a {@code WindowManager} with a new {@code JFrame} titled "Concorde".
     * The window has an initial size of 800x600 pixels and is centered on the screen.
     * It also sets the default close operation to {@link JFrame#DO_NOTHING_ON_CLOSE}.
     */
    public WindowManager() {
        frame = new JFrame("Concorde");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        setIconImage();
    }

    /**
     * Sets the icon image for the window. This method is currently commented out but can be used to set a custom icon.
     *
     * @deprecated The icon setting functionality is currently disabled.
     */
    private void setIconImage() {
        //ImageIcon icon = new ImageIcon(getClass().getResource("./src/main/java/View/components/coding.png"));
       // frame.setIconImage(icon.getImage());
    }

    /**
     * Sets the content pane for the window. This replaces the current content pane with the specified one.
     * After setting the new content pane, it triggers a revalidation and repainting of the window.
     *
     * @param contentPane the new content pane to set for the window.
     */
    public void setContentPane(Container contentPane) {
        frame.setContentPane(contentPane);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Adds a {@link WindowListener} to handle window events such as opening, closing, or resizing the window.
     *
     * @param listener the {@code WindowListener} to add to the window.
     */
    public void addWindowListener(WindowListener listener) {
        frame.addWindowListener(listener);
    }

    /**
     * Makes the window visible to the user by setting it to be visible.
     */
    public void showWindow() {
        frame.setVisible(true);
    }

    /**
     * Closes the window by disposing the {@code JFrame}.
     */
    public void closeWindow() {
        frame.dispose();
    }

    /**
     * Returns the underlying {@code JFrame} instance.
     *
     * @return the {@code JFrame} used by the {@code WindowManager}.
     */
    public JFrame getFrame() {
        return frame;
    }
}