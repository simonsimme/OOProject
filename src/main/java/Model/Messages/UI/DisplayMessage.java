package Model.Messages.UI;

import java.time.LocalDateTime;

/**
 * Represents a message to be displayed in the user interface,
 * containing details about the sender, the message content, and the channel.
 */
public class DisplayMessage implements UIMessage {
    /**
     * The username of the sender of the message.
     */
    private final String userName;

    /**
     * The content of the message to be displayed.
     */
    private final String message;

    /**
     * The name of the channel where the message was sent.
     */
    private final String channelName;

    private LocalDateTime time;

    /**
     * Constructs a {@code DisplayMessage} with the specified sender, message, and channel.
     *
     * @param userName    The username of the sender.
     * @param message     The content of the message.
     * @param channelName The name of the channel where the message was sent.
     */
    public DisplayMessage(String userName, String message, String channelName){
        this.userName = userName;
        this.message = message;
        this.channelName = channelName;
        this.time = LocalDateTime.now();
    }

    /**
     * Retrieves the name of the channel where the message was sent.
     *
     * @return The name of the channel.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Retrieves the username of the sender of the message.
     *
     * @return The username of the sender.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Retrieves the content of the message to be displayed.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Accepts a visitor, allowing it to handle the {@code DisplayMessage}.
     * @param visitor The {@link UIMessageVisitor} that will handle the message.
     */
    @Override
    public void accept(UIMessageVisitor visitor) {
         visitor.handle(this);
    }

    /**
     * Retrieves the time of the message to be displayed.
     *
     * @return time of the message
     */
    public LocalDateTime getTimestamp(){
        return time;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.time = timestamp;
    }
}
