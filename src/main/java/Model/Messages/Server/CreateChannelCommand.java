package Model.Messages.Server;
/**
 * Represents a command to create a new channel on the server.
 * This command contains information about the user requesting the channel creation,
 * the channel name, and an optional password for the channel.
 */
public class CreateChannelCommand extends ServerMessage {
    private final String userName;
    private final String channelName;
    private final String channelPassword;
    //TODO : Add channel settings ?
    /**
     * Constructs a {@code CreateChannelCommand} with the specified user name and channel name.
     * The channel will be created without a password.
     *
     * @param userName    the name of the user creating the channel.
     * @param channelName the name of the channel to be created.
     */
    public CreateChannelCommand(String userName,String channelName){
     this(userName,channelName,"");
    }
    /**
     * Constructs a {@code CreateChannelCommand} with the specified user name, channel name, and channel password.
     *
     * @param userName       the name of the user creating the channel.
     * @param channelName    the name of the channel to be created.
     * @param channelPassword the password for the channel, or an empty string if no password is required.
     */
    public CreateChannelCommand(String userName, String channelName, String channelPassword){
        this.userName = userName;
        this.channelName = channelName;
        this.channelPassword = channelPassword;
    }
    /**
     * Returns the password for the channel.
     *
     * @return the channel password, or an empty string if no password is required.
     */
    public String getChannelPassword() {
        return channelPassword;
    }
    /**
     * Returns the name of the channel to be created.
     *
     * @return the channel name.
     */
    public String getUserName() {
        return userName;
    }

    public String getChannelName() {
        return channelName;
    }
    /**
     * Accepts a visitor that processes this command.
     *
     * @param serverMessageVisitor the visitor handling this command.
     */
    public void accept(ServerMessageVisitor serverMessageVisitor)
    {
        serverMessageVisitor.handle(this);
    }
}
