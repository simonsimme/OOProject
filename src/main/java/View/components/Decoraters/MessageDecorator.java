// MessageDecorator.java
package View.components.Decoraters;

import Model.Messages.UI.DisplayMessage;
import View.components.IView;
import View.components.TextFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A decorator class for formatting and appending chat messages with different styles.
 * This class processes and styles chat messages by detecting patterns such as color commands,
 * "@username" mentions, and code blocks, and applies appropriate colors and fonts to them.
 * It extends the {@link ViewDecorator} class to decorate the base view with formatted text.
 */
public class MessageDecorator extends ViewDecorator {

    /**
     * Constructs a MessageDecorator that decorates the provided view.
     *
     * @param decoratedView The IView object to be decorated.
     */
    public MessageDecorator(IView decoratedView) {
        super(decoratedView);
    }

    /**
     * Appends the chat text to the view, applying formatting such as color, font style,
     * and handling special patterns (e.g., color codes, @username, and code blocks).
     *
     * @param text The DisplayMessage object containing the message details such as timestamp, username, and content.
     */
    @Override
    public void appendChatText(DisplayMessage text) {
        String timestamp = text.getTimestamp().getHour() + "." + text.getTimestamp().getMinute();
        String userName = " - " + text.getUserName() + " - ";
        String message = text.getMessage();

        List<String> messageList = new ArrayList<>();
        List<Color> colorList = new ArrayList<>();
        List<Font> fontList = new ArrayList<>();

        messageList.add(timestamp);
        colorList.add(Color.gray);
        fontList.add(new Font("Arial", Font.PLAIN, 12));

        messageList.add(userName);
        colorList.add(Color.red);
        fontList.add(new Font("Arial", Font.BOLD, 12));

        // Combine patterns: color commands, @username, and code blocks
        Pattern combinedPattern = Pattern.compile(
                "/(red|blue|green|yellow|purple|orange)\\s+\"([^\"]*)\"|(@\\w+)|```(.*?)```",
                Pattern.DOTALL
        );
        Matcher matcher = combinedPattern.matcher(message);
        int lastEnd = 0;

        while (matcher.find()) {
            // Add text before the match
            if (lastEnd < matcher.start()) {
                messageList.add(message.substring(lastEnd, matcher.start()));
                colorList.add(Color.gray);
                fontList.add(new Font("Arial", Font.PLAIN, 12));
            }

            if (matcher.group(1) != null) {
                // Color command
                String color = matcher.group(1);
                String coloredText = matcher.group(2);
                Color textColor = getColorFromString(color);
                messageList.add(coloredText);
                colorList.add(textColor);
                fontList.add(new Font("Arial", Font.PLAIN, 12));
            } else if (matcher.group(3) != null) {
                // @username pattern
                String username = matcher.group(3);
                messageList.add(username);
                colorList.add(Color.yellow);
                fontList.add(new Font("Arial", Font.BOLD, 12));
            } else if (matcher.group(4) != null) {
                // Code block pattern
                String codeBlock = matcher.group(4);
                messageList.add(codeBlock);
                colorList.add(Color.CYAN);
                fontList.add(new Font("Monospaced", Font.PLAIN, 12)); // Use a monospaced font for code
            }

            lastEnd = matcher.end();
        }

        // Add remaining text after the last match
        if (lastEnd < message.length()) {
            messageList.add(message.substring(lastEnd));
            colorList.add(Color.gray);
            fontList.add(new Font("Arial", Font.PLAIN, 12));
        }

        // Create TextFormat and pass it to the decorated view
        TextFormat tf = new TextFormat(messageList, colorList, fontList);
        super.appendChatText(tf);
    }

    /**
     * Converts a string representing a color into a corresponding {@link Color} object.
     *
     * @param color The string representing the color (e.g., "red", "blue", etc.).
     * @return The corresponding {@link Color} object.
     */
    private Color getColorFromString(String color) {
        return switch (color) {
            case "red" -> Color.red;
            case "blue" -> Color.blue;
            case "green" -> Color.green;
            case "yellow" -> Color.yellow;
            case "purple" -> new Color(128, 0, 128); // Custom purple color
            case "orange" -> Color.orange;
            default -> Color.gray;
        };
    }
}