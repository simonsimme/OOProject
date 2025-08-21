package View.components;

import Model.Messages.UI.DisplayMessage;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * The ChatArea class represents the chat display area of the application.
 * It contains a JTextPane for displaying chat messages.
 */
public class ChatArea {
    private final JTextPane chatArea;

    /**
     * Constructs a new ChatArea and initializes the UI components.
     */
    public ChatArea() {
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBackground(new Color(60, 63, 65));
        chatArea.setForeground(Color.WHITE);
        chatArea.setContentType("text/plain");
    }

    /**
     * Returns a JScrollPane containing the chat area.
     *
     * @return the JScrollPane containing the chat area
     */
    public JScrollPane getChatScrollPane() {
        return new JScrollPane(chatArea);
    }

    /**
     * Clears the chat text area.
     */
    public void clearChatText() {
        chatArea.setText("");
    }

    /**
     * Appends plain text to the chat area.
     *
     * @param text the text to append
     */
    public void appendChatText(String text) {
        try {
            StyledDocument doc = chatArea.getStyledDocument();
            doc.insertString(doc.getLength(), text + "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends formatted text to the chat area.
     *
     * @param tf the TextFormat containing the text and colors to append
     */
    public void appendChatText(TextFormat tf) {

        StyledDocument doc = chatArea.getStyledDocument();
        for (int i = 0; i < tf.getText().size(); i++) {
            Style style = chatArea.addStyle("ColorStyle", null);
            StyleConstants.setForeground(style, tf.getColors().get(i));
            try {
                doc.insertString(doc.getLength(), tf.getText().get(i), style);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        try {
            doc.insertString(doc.getLength(), "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends a DisplayMessage to the chat area.
     *
     * @param text the DisplayMessage to append
     */
    public void appendChatText(DisplayMessage text) {
        try {
            StyledDocument doc = chatArea.getStyledDocument();
            doc.insertString(doc.getLength(), text.getUserName() + ": " + text.getMessage() + "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the chat history in the chat area.
     *
     * @param history the chat history to display
     */
    public void showHistory(StringBuilder history) {
        chatArea.setText(history.toString());
    }
}