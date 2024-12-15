package Model.Messages.Client;

/**
 * Represents a response message sent to a client, indicating that they have successfully left a channel.
 * This class extends {@code ClientMessage} and includes the name of the channel that the client left.
 */
public class LeaveChannelResponse extends ClientMessage {
    /**
     * This string represents the name of the channel that the client has left
     */
    private final String channelName;

    /**
     * Message to client : Leave the given channel
     * @param channelName The name of the channel that the client has left
     */
    public LeaveChannelResponse(String channelName){
        this.channelName = channelName;
    }

    /**
     * Gets the name of the channel that the client has left.
     *
     * @return The name of the channel.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Accepts a visitor to handle this {@code LeaveChannelResponse} message as part of the Visitor pattern.
     * @param visitor The visitor that processes this message.
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}