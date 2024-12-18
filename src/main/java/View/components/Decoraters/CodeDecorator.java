// CodeDecorator.java
package View.components.Decoraters;

import Model.Messages.UI.DisplayMessage;
import View.components.IView;
import View.components.TextFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeDecorator extends ViewDecorator {

    public CodeDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void appendChatText(DisplayMessage text) {
        String timestamp = text.getTimestamp().getHour() + "." + text.getTimestamp().getMinute();
        String userName = " - " + text.getUserName() + " - ";
        String message = text.getMessage();

        List<String> messageList = new ArrayList<>();
        List<Color> colorList = new ArrayList<>();
        List<Font> fontList = new ArrayList<>();

        // Default font
        Font defaultFont = new Font("Arial", Font.PLAIN, 12);
        Font codeFont = new Font("Courier New", Font.PLAIN, 12);

        messageList.add(timestamp);
        colorList.add(Color.gray);
        fontList.add(defaultFont);

        messageList.add(userName);
        colorList.add(Color.red);
        fontList.add(defaultFont);

        // Regular expression to match code blocks, e.g., enclosed in backticks (`code here`)
        Pattern codePattern = Pattern.compile("`([^`]*)`");
        Matcher matcher = codePattern.matcher(message);

        int lastEnd = 0;

        while (matcher.find()) {
            // Add non-code text before the code block
            messageList.add(message.substring(lastEnd, matcher.start()));
            colorList.add(Color.gray);
            fontList.add(defaultFont);

            // Insert a newline before the code block to separate it visually
            messageList.add("\n");
            colorList.add(Color.gray);
            fontList.add(defaultFont);

            // Add code block text
            String codeText = matcher.group(1);
            messageList.add(codeText);
            colorList.add(Color.cyan); // Example: blue for code
            fontList.add(codeFont);

            lastEnd = matcher.end();
        }

        // Add remaining text after the last code block
        messageList.add(message.substring(lastEnd));
        colorList.add(Color.gray);
        fontList.add(defaultFont);

        // Create TextFormat with fonts
        TextFormat tf = new TextFormat(messageList, colorList, fontList);
        super.appendChatText(tf);
    }
}
