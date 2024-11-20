// TimestampDecorator.java
package View;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class TimestampDecorator extends ViewDecorator {

    public TimestampDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void appendChatText(String text) {
        String timestampedText = LocalDateTime.now().getHour() + "." + LocalDateTime.now().getMinute() + " - "+text;
        super.appendChatText(timestampedText);
    }
}