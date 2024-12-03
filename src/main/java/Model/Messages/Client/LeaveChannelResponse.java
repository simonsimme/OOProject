package Model.Messages.Client;

/**
 * Message to client : You have left the given channel
 */
public class LeaveChannelResponse extends ClientMessage {
    /**
     * This string represents the name of the channel that the client has left
     */
    private String channelName;

    /**
     * Message to client : Leave the given channel
     * @param channelName The name of the channel that the client has left
     */
    public LeaveChannelResponse(String channelName){
        this.channelName = channelName;
    }

    //Getter
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
