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
        String timestampedText = text.getTimestamp().getHour() + "." + text.getTimestamp().getMinute() + " - " ;//+text.getContent(); //TODO fix this
        super.appendChatText(timestampedText);
    }
}