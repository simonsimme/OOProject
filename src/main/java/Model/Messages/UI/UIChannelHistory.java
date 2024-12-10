package Model.Messages.UI;

import java.util.ArrayList;
import java.util.List;

public class UIChannelHistory extends UIMessage {
    private String history;

    public UIChannelHistory(String history){
        this.history = history;
    }

    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }

    public List<DisplayMessage> parseHistory() {
        List<DisplayMessage> messages = new ArrayList<>();
        String[] lines = history.split(System.lineSeparator());
        for (String line : lines) {
            if (line.contains("Message:")) {
                String[] parts = line.split(" Message: ");
                if (parts.length == 2) {
                    String timestamp = parts[0].trim();
                    String[] messageParts = parts[1].split(" from ");
                    if (messageParts.length == 2) {
                        String message = messageParts[0].trim();
                        String sender = messageParts[1].trim();
                        messages.add(new DisplayMessage(sender, message));
                    }
                }
            }
        }
        return messages;
    }
}
