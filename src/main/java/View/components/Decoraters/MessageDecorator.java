// MessageDecorator.java
package View.components.Decoraters;

import View.components.IView;
import View.components.TextFormat;
import Model.Messages.UI.DisplayMessage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageDecorator extends ViewDecorator {

    public MessageDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void appendChatText(DisplayMessage text) {
        String timestamp = text.getTimestamp().getHour() + "." + text.getTimestamp().getMinute();
        String userName = " - " + text.getUserName() + " - ";
        String message = text.getMessage();

        List<String> messageList = new ArrayList<>();
        List<Color> colorList = new ArrayList<>();

        messageList.add(timestamp);
        colorList.add(Color.gray);

        messageList.add(userName);
        colorList.add(Color.red);

        // Combine color and username patterns
        Pattern combinedPattern = Pattern.compile("/(red|blue|green|yellow|purple|orange)\\s+\"([^\"]*)\"|(@\\w+)");
        Matcher matcher = combinedPattern.matcher(message);
        int lastEnd = 0;

        while (matcher.find()) {
            messageList.add(message.substring(lastEnd, matcher.start()));
            colorList.add(Color.gray);

            if (matcher.group(1) != null) {
                // Color command
                String color = matcher.group(1);
                String coloredText = matcher.group(2);
                Color textColor = getColorFromString(color);
                messageList.add(coloredText);
                colorList.add(textColor);
            } else if (matcher.group(3) != null) {
                // @username pattern
                String username = matcher.group(3);
                messageList.add(username);
                colorList.add(Color.yellow);
            }

            lastEnd = matcher.end();
        }

        messageList.add(message.substring(lastEnd));
        colorList.add(Color.gray);

        TextFormat tf = new TextFormat(messageList, colorList);
        super.appendChatText(tf);
    }

    private Color getColorFromString(String color) {
        switch (color) {
            case "red":
                return Color.red;
            case "blue":
                return Color.blue;
            case "green":
                return Color.green;
            case "yellow":
                return Color.yellow;
            case "purple":
                return new Color(128, 0, 128); // Custom purple color
            case "orange":
                return Color.orange;
            default:
                return Color.gray;
        }
    }
}