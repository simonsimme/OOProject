package backend;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The Message class represents a message sent by a client.
 * It implements Serializable to allow the message to be sent over a network.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    // The content of the message
    private String content;

    // The sender of the message
    private String sender;

    // The timestamp when the message was created
    private LocalDateTime timestamp;
    // The CommandType of the message sent
    private CommandType commandType;

    /**
     * Constructs a new Message with the specified content and sender.
     * The timestamp is set to the current time.
     * By default, sets the commandType to MESSAGE
     *
     * @param content the content of the message
     * @param sender the sender of the message
     */
    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
        this.commandType = CommandType.MESSAGE;
    }

    /**
     * Constructs a new Message with the specified content, sender and commandType.
     * The timestamp is set to the current time.
     *
     * @param content the content of the message
     * @param sender the sender of the message
     * @param commandType the command the message asks the server to preform
     */
    public Message(String content, String sender, CommandType commandType) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
        this.commandType = commandType;
    }

    /**
     * Returns the content of the message.
     *
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the sender of the message.
     *
     * @return the sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns the timestamp of the message.
     *
     * @return the timestamp of the message
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a CommandType representation of the type of command the message should preform
     * @return CommandType of the message
     */
    public CommandType getCommandType() {
        return commandType;
    }

    /**
     * Returns a string representation of the message.
     *
     * @return a string representation of the message
     */

    @Override
    public String toString() {
        return "Message from " + sender + " at " + timestamp + ": " + content;
    }
}