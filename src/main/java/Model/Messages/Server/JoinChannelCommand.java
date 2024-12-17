package Model.Messages.Server;
/**
 * Represents a command for a user to join a specific channel.
 * The command includes the user's name, the channel name, and an optional password for the channel.
 */
public class JoinChannelCommand implements ServerMessage, ServerVisitableMessage {
    private final String userName;
    private final String channelName;
    private final String password;
    /**
     * Constructs a {@code JoinChannelCommand} with the specified userName, channelName, and password.
     *
     * @param userName    the name of the user attempting to join the channel.
     * @param channelName the name of the channel the user wants to join.
     * @param password    the password required to join the channel (can be empty if no password is needed).
     */
    public JoinChannelCommand(String userName, String channelName, String password){
        this.userName = userName;
        this.channelName = channelName;
        this.password = password;
    }
    /**
     * Returns the name of the channel the user wants to join.
     *
     * @return the channel name.
     */
    public String getChannelName() {
        return channelName;
    }
    /**
     * Returns the name of the user attempting to join the channel.
     *
     * @return the user's name.
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Returns the password required to join the channel.
     *
     * @return the channel password.
     */
    public String getPassword(){
        return password;
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
