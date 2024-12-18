package Model.Messages.Client;

/**
 * Represents a response message sent to a client, indicating that they have successfully joined a channel.
 * This class extends {@code ClientMessage} and includes the name of the channel that the client joined.
 */
public class JoinChannelResponse implements ClientMessage {
    /**
     * This string represents the name of the channel that the user has joined.
     */
    private final String channelName;

    /**
     * Constructs a {@code JoinChannelResponse} with the given channel name.
     * @param channelName The name of the channel that the user has joined.
     */
    public JoinChannelResponse(String channelName){
        this.channelName = channelName;
    }

    /**
     * Gets the name of the channel that the user has joined.
     * @return The name of the channel.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Accepts a visitor to handle this {@code JoinChannelResponse} message as part of the Visitor pattern.
     * @param visitor The visitor that processes this message.
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
         visitor.handle(this);
    }
}
