package Model.Messages.Client;

/**
 * Message to  client : Join the gi ven channel
 */
public class JoinChannelResponse extends ClientMessage {
    /**
     * This string represents the name of the channel that the user has joined.
     */
    private String channelName;

    /**
     * Constructor.
     * @param channelName The name of the channel that the user has joined.
     */
    public JoinChannelResponse(String channelName){
        this.channelName = channelName;
    }

    //Getter
    public String getChannelName() {
        return channelName;
    }

    /**
     * Visitor pattern method.
     * @param visitor
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
         visitor.handle(this);
    }
}
