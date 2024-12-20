package View.components;

import javax.swing.*;

/**
 * The {@code HelpManager} class provides static methods to display help dialogs to users.
 * These dialogs provide information about the application features and available commands.
 * <p>
 * The class includes methods to show general help information and specific command help, which assist users in understanding
 * how to interact with the chat application and its features.
 * </p>
 */
public class HelpManager {

    /**
     * Displays a general help dialog with information about the chat application's features.
     * The dialog provides users with information on how to create a channel, join a channel,
     * change nickname, send messages, and more.
     */
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

    /**
     * Displays a command help dialog with information about available text formatting commands.
     * The dialog shows users how to use commands to change the color of text, mention users,
     * and format messages as code.
     */
    public static void showCommandHelpDialog() {
        String helpMessage = "Command Help:\n\n"
                + "/red \"text\" - Display text in red color.\n"
                + "/blue \"text\" - Display text in blue color.\n"
                + "/green \"text\" - Display text in green color.\n"
                + "/yellow \"text\" - Display text in yellow color.\n"
                + "/purple \"text\" - Display text in purple color.\n"
                + "/orange \"text\" - Display text in orange color.\n"
                + "@username - Mention a user.\n"
                + "```code``` - Display code in cyan color.";
        JOptionPane.showMessageDialog(null, helpMessage, "Command Help", JOptionPane.INFORMATION_MESSAGE);
    }
}