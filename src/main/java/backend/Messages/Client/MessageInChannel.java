package backend.Messages.Client;
/**
 * Message to client : Send a message in a channel to a client
 */
public class MessageInChannel extends ClientMessage implements ClientVisitableMessage {
    /**
     * The user name of the sender of the message. This string is displayed before the message in the UI.
     */
    private String userName;
    /**
     * The channel name of the channel that the message is sent to.
     */
    private String channelName;

    /**
     * This string is displayed in the chatbox i nthe given channel
     */
    private String message;

    /**
     * Constructor
     * @param userName This string is displayed before the message in the UI.
     * @param channelName The name of the channel that the message is sent in.
     * @param message This string is displayed as the message in the chatbox in the given channel.
     */
    public MessageInChannel(String userName, String channelName, String message){
        this.userName = userName;
        this.channelName = channelName;
        this.message = message;
    }

    //Getters
    public String getChannelName() {return channelName;}
    public String getMessage(){return message;}
    public String getUserName() {return userName;}

    /**
     * Visitor pattern method.
     * @param visitor
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
