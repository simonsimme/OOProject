// TimestampDecorator.java
package View;

import backend.Messages.*;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class TimestampDecorator extends ViewDecorator {

    public TimestampDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void appendChatText(Message text) {
        String timestampedText = LocalDateTime.now().getHour() + "." + LocalDateTime.now().getMinute() + " - " ;//+text.getContent(); //TODO fix this
        super.appendChatText(timestampedText);
    }

    @Override
    public String getPasswordInput() {
        return null;
    }

    @Override
    public String getChannelNameInput() {
        return null;
    }
}