package Model.Messages.Server;

/**
 * Represents a command to retrieve the chat history of a specified channel.
 * <p>
 * This command is used to request historical messages for a given channel
 * on behalf of a user.
 */
public class RetrieveChatHistoryCommand implements ServerMessage, ServerVisitableMessage {

    /**
     * The name of the channel for which chat history is being requested.
     */
    private String channelName;

    /**
     * The name of the user requesting the chat history.
     */
    private String userName;

    /**
     * Constructs a {@code RetrieveChatHistoryCommand} with the specified username and channel name.
     *
     * @param userName    the name of the user requesting the chat history
     * @param channelName the name of the channel whose chat history is being requested
     */
    public RetrieveChatHistoryCommand(String userName, String channelName) {
        this.channelName = channelName;
        this.userName = userName;
    }

    /**
     * Retrieves the name of the channel.
     *
     * @return the name of the channel
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Accepts a visitor to handle this {@code RetrieveChatHistoryCommand}.
     *
     * @param visitor the visitor that will process this command
     */
    @Override
    public void accept(ServerMessageVisitor visitor) {
        visitor.handle(this);
    }
}
