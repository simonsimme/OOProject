package backend.Messages.Client;

/**
 * Message to client : Leave the given channel
 */
public class LeaveChannelResponse extends ClientMessage {
    private String channelName;

    /**
     * Message to client : Leave the given channel
     * @param channelName
     */
    public LeaveChannelResponse(String channelName){
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    /**
     *  Visitor pattern method.
     * @param visitor
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
