package View.components;

import javax.swing.*;

public class HelpManager {
    public static void showHelpDialog() {
        String helpMessage = "This is the help information for the chat application.\n\n"
                + "1. Create Channel: Click to create a new chat channel.\n"
                + "2. Join Channel: Click to join an existing chat channel.\n"
                + "3. Change Your Nickname: Enter a new nickname in the text field.\n"
                + "4. Send Message: Type your message and press Enter to send.\n"
                + "5. Join New Channel: Click to join a new chat channel.\n"
                + "6. Leave Channel: Click to leave the current chat channel.\n"
                + "7. Create New Channel: Click to create a new chat channel.";

        JOptionPane.showMessageDialog(null, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showCommandHelpDialog() {
        String helpMessage = "Command Help:\n\n"
                + "/red \"text\" - Display text in red color.\n"
                + "/blue \"text\" - Display text in blue color.\n"
                + "/green \"text\" - Display text in green color.\n"
                + "/yellow \"text\" - Display text in yellow color.\n"
                + "/purple \"text\" - Display text in purple color.\n"
                + "/orange \"text\" - Display text in orange color.\n"
                + "@username - Mention a user.";
        JOptionPane.showMessageDialog(null, helpMessage, "Command Help", JOptionPane.INFORMATION_MESSAGE);
    }
}