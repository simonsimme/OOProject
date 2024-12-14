package Model.Messages.Client;

import java.util.List;

/**
 * Message to  client : Join the gi ven channel
 */
public class JoinChannelResponse extends ClientMessage {
    /**
     * This string represents the name of the channel that the user has joined.
     */
    private final String channelName;
    private final List<String> users;

    /**
     * Constructor.
     * @param channelName The name of the channel that the user has joined.
     */
    public JoinChannelResponse(String channelName, List<String> users){
        this.channelName = channelName;
        this.users = users;
    }

    //Getter
    public String getChannelName() {
        return channelName;
    }
    public List<String> getUsers() {
        return users;
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
