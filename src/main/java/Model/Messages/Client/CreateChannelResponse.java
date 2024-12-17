package Model.Messages.Client;
/**
 * Message to client : Channel has been created.
 * This message contains information about the newly created channel.
 */
public class CreateChannelResponse implements ClientMessage, ClientVisitableMessage {
    /**
     * This string represents the name of the channel that was created.
     */
    private final String channelName;
    /**
     * Constructor to create a response message with the name of the created channel.
     * @param channelName the name of the channel that was created.
     */
    public CreateChannelResponse(String channelName){
        this.channelName = channelName;
    }
    /**
     * Gets the name of the channel that was created
     * @return the name of the created channel.
     */
    public String getChannelName(){
        return channelName;
    }
    /**
     * Accepts a visitor to handle this CreateChannelResponse message.
     * This method follows the visitor design pattern to allow actions to be preformed on this message.
     * @param visitor the visitor object that will handle this message.
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }

}