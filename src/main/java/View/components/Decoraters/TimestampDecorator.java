// TimestampDecorator.java
package View.components.Decoraters;

import View.components.IView;
import View.components.TextFormat;
import Model.Messages.UI.DisplayMessage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TimestampDecorator extends ViewDecorator {

    public TimestampDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void appendChatText(DisplayMessage text) {
        String timestamp = text.getTimestamp().getHour() + "." + text.getTimestamp().getMinute();
        String userName = " - " + text.getUserName()+ " - ";
        String message = text.getMessage();
        List<String> messageList = new ArrayList<>();
        messageList.add(timestamp);
        messageList.add(userName);
        messageList.add(message);
        List<Color> colorList = new ArrayList<>();
        colorList.add(Color.gray);
        colorList.add(Color.red);
        colorList.add(Color.gray);
        TextFormat tf = new TextFormat(messageList,colorList);

        super.appendChatText(tf);
    }
}