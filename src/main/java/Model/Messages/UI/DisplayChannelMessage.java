package Model.Messages.UI;

/**
* Represents a message to display a channel message in the UI.
* This class is part of the message model for the UI system.
*/
public class DisplayChannelMessage implements UIMessage {
    /**
     * The message content to be displayed for a channel.
     */
    private final String channelMessage;

    /**
     * Constructs a {@code DisplayChannelMessage} with the specified channel message.
     *
     * @param channelMessage the message content to be displayed
     */
    public DisplayChannelMessage(String channelMessage) {
        this.channelMessage = channelMessage;
    }

    /**
     * Retrieves the channel message content.
     *
     * @return the channel message as a string
     */
    public String getChannelMessage()
    {
        return channelMessage;
    }

    /**
     * Accepts a visitor to handle this {@code DisplayChannelMessage}.
     *
     * @param visitor the visitor that will handle this message
     */
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}