package backend.Messages.Client;

/**
 * Message to client : Channel has been created.
 */
public class CreateChannelResponse extends ClientMessage {

    /**
     * This string represents the name of the channel that was created.
     */
    private String channelName;
    //TODO : Add channel settings?

    /**
     * Constructor
     * @param channelName the name of the channel that was created.
     */
    public CreateChannelResponse(String channelName){
        this.channelName = channelName;
    }

    //Getter
    public String getChannelName(){
        return channelName;
    }

    /**
     * Visitor method
     * @param visitor
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
