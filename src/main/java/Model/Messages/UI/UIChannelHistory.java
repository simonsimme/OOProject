package Model.Messages.UI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the history of a channel sent to the user interface.
 * This class provides functionality to parse the history string into individual messages.
 */
public class UIChannelHistory implements UIMessage {
    /**
     * The history of the channel as a single string.
     */
    private final String history;

    /**
     * Constructs a {@code UIChannelHistory} instance with the given history string.
     *
     * @param history The history of the channel as a string.
     */
    public UIChannelHistory(String history){
        this.history = history;
    }

    /**
     * Parses the history string and returns a list of DisplayMessages.
     * @return List of DisplayMessages.
     */
    public List<DisplayMessage> parseHistory() {

        List<DisplayMessage> messages = new ArrayList<>();

        StringBuilder historyString = new StringBuilder();
        historyString.append("\n");
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
                        LocalDateTime time = LocalDateTime.parse(timestamp);
                        DisplayMessage dm = new DisplayMessage(sender, message, "---");
                        dm.setTimestamp(time);
                        messages.add(dm);
                    }
                }
            }
        }
        System.out.println(historyString.toString());
        return messages;
    }

    /**
     * Accepts a visitor, allowing it to handle the {@code UIChannelHistory}.
     * @param visitor The {@link UIMessageVisitor} that will handle the message.
     */
    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}
