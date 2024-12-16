package Model.Messages.Server;

/**
 * Represents a command to send a message in a specific channel.
 * Extends {@link ServerMessage} and contains the necessary information
 * for sending a message, including the user, channel, and message content.
 */
public class SendMessageInChannelCommand extends ServerMessage {
    private final String userName;
    private final String channelName;
    private final String message;

    public boolean isServerMessage() {
        return isServerMessage;
    }

    private final boolean isServerMessage;

    /**
     * Constructs a new {@code SendMessageInChannelCommand} with the specified details.
     *
     * @param userName    the username of the sender.
     * @param channelName the name of the channel where the message will be sent.
     * @param message     the content of the message being sent.
     */
    public SendMessageInChannelCommand(String userName, String channelName, String message, boolean isServerMessage){
        this.userName = userName;
        this.channelName = channelName;
        this.message = message;
        this.isServerMessage = isServerMessage;
    }
    /**
     * Retrieves the message content.
     *
     * @return the message content as a string.
     */
    public String getMessage() {
        return message;
    }
    /**
     * Retrieves the username of the sender.
     *
     * @return the username of the sender.
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Retrieves the name of the channel where the message is sent.
     *
     * @return the name of the channel.
     */
    public String getChannelName() {
        return channelName;
    }
    /**
     * Accepts a {@code ServerMessageVisitor} to handle this message command.
     * This method allows the visitor to perform an operation on the command,
     * typically by calling its {@code handle} method.
     *
     * @param serverMessageVisitor the visitor that will handle this command.
     */
    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.handle(this);
    }

}
