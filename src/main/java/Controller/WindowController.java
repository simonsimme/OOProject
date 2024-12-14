package Controller;

import Model.Client.Client;
import View.components.IView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowController {
    private final IView view;
    private final Client reference;

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
