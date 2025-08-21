package Controller;

import Model.Client.Client;
import View.components.IView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The {@code WindowController} class handles the window events, particularly the window closing event.
 * It prompts the user with an exit confirmation dialog when the user attempts to close the application window.
 * If the user confirms, it disconnects the client and closes the application.
 */
public class WindowController {
    private final IView view;
    private final Client reference;

    /**
     * Constructs a new {@code WindowController} to handle window events.
     * This constructor sets up a listener for the window closing event.
     *
     * @param view     the view interface that represents the application's window
     * @param reference the client instance associated with the user
     */
    public WindowController(IView view, Client reference) {
        this.view = view;
        this.reference = reference;
        this.view.addWindowExitListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });
    }

    /**
     * Handles the window closing event by displaying a confirmation dialog to the user.
     * If the user confirms the closure, the application will disconnect the client and close the window.
     */
    private void handleWindowClosing() {
        int confirm = JOptionPane.showOptionDialog(
                null,
                "Are You Sure to Close the Application?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        if (confirm == JOptionPane.YES_OPTION) {
            view.closeWindow();
            reference.disconnect();
            System.exit(0);
        }
    }
}
