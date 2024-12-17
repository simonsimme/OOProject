package Model.Messages.Server;
/**
 * Represents a command for a user to leave a specific channel.
 * The command includes the user's name and the channel name.
 */
public class LeaveChannelCommand implements ServerMessage, ServerVisitableMessage {
    private final String userName;
    private final String channelName;
    /**
     * Constructs a {@code LeaveChannelCommand} with the specified username and channel name.
     *
     * @param userName    the name of the user leaving the channel.
     * @param channelName the name of the channel the user wants to leave.
     */
    public LeaveChannelCommand(String userName, String channelName){
        this.userName = userName;
        this.channelName = channelName;
    }

    /**
     * Returns the name of the channel the user wants to leave.
     * @return the channel name.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Returns the username of the user leaving the channel
     * @return the users name.
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Accepts a {@code ServerMessageVisitor} to handle this command.
     * This method follows the Visitor design pattern.
     *
     * @param serverMessageVisitor the visitor that will handle this command.
     */
    public void accept(ServerMessageVisitor serverMessageVisitor)
    {
        serverMessageVisitor.handle(this);
    }
}
