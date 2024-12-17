package Model.Messages.Client;

import java.time.LocalDateTime;

/**
 * Represents a message sent to a client, indicating that a message has been posted in a specific channel.
 * This class extends {@code ClientMessage} and includes details about the sender, the channel, and the message content.
 */
public class MessageInChannel implements ClientMessage, ClientVisitableMessage {
    /**
     * The username of the sender of the message.
     * This string is displayed before the message in the user interface.
     */
    private final String userName;
    /**
     * The channel name of the channel that the message is sent to.
     */
    private final String channelName;

    /**
     * The content of the message.
     */
    private final String message;

    private final boolean isServerMessage;

    private final LocalDateTime time;

    /**
     * Constructs a {@code MessageInChannel} with the specified sender, channel, and message content.
     *
     * @param userName    The name of the user sending the message.
     * @param channelName The name of the channel where the message was sent.
     * @param message     The content of the message.
     */
    public MessageInChannel(String userName, String channelName, String message, boolean isServerMessage){
        this.userName = userName;
        this.channelName = channelName;
        this.message = message;
        this.isServerMessage = isServerMessage;
        time = LocalDateTime.now();
    }
    public boolean isServerMessage()
    {
        return isServerMessage;
    }

    /**
     * Gets the name of the channel where the message was sent.
     *
     * @return The channel name.
     */
    public String getChannelName() { return channelName; }

    /**
     * Gets the content of the message.
     *
     * @return The message content.
     */
    public String getMessage(){ return message; }

    /**
     * Gets the name of the user who sent the message.
     *
     * @return The username.
     */
    public String getUserName() { return userName; }

    /**
     * Accepts a visitor to handle this {@code MessageInChannel} as part of the Visitor pattern.
     * @param visitor The visitor that processes this message.
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }


    @Override
    public String toString() {
        return "Time: " + time.getHour() + time.getMinute() + time.getSecond() + " From: " + userName + " inChannel: " + channelName;
    }

}
