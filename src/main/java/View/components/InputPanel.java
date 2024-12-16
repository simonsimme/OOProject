package View.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The InputPanel class represents the input area of the chat application.
 * It contains a text area for user input and a send button.
 */
public class InputPanel extends JPanel {
    private JTextArea inputField;
    private JButton sendButton;

    /**
     * Constructs a new InputPanel and initializes the UI components.
     */
    public InputPanel() {

        inputField = new JTextArea(1, 20);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBackground(new Color(60, 63, 65));
        inputField.setForeground(Color.WHITE);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        sendButton = new JButton("Send");


        setLayout(new BorderLayout());
        add(new JScrollPane(inputField), BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);

        // Add key listener to the input field to send message on Enter key press
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !e.isShiftDown()) {
                    sendButton.doClick();
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown()) {
                    inputField.append("\n");
                    e.consume();
                }
            }
        });
    }

    /**
     * Returns the input panel component.
     *
     * @return the input panel component
     */
    public JPanel getInputPanel() {
        return this;
    }

    /**
     * Returns the text from the input field.
     *
     * @return the input text
     */
    public String getInputText() {
        return inputField.getText();
    }

    /**
     * Clears the text in the input field.
     */
    public void clearInputText() {
        inputField.setText("");
        inputField.setCaretPosition(0);
    }

    /**
     * Adds a listener for the send button.
     *
     * @param listener the ActionListener to add
     */
    public void addSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    /**
     * Displays an error message in a dialog.
     *
     * @param message the error message to display
     */
    private void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}