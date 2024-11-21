package backend.Messages;
import backend.Messages.Server.ServerMessageVisitor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The {@code Message} class represents a message sent in a chat system.
 * This abstract class serves as the base for different types of messages
 * that can be sent between clients and the server.
 * <p>
 * Each message includes the following:
 * <ul>
 *   <li>The content of the message</li>
 *   <li>The sender of the message</li>
 *   <li>The timestamp indicating when the message was created</li>
 * </ul>
 * Subclasses can extend this class to implement specific types of messages,
 * each corresponding to a particular command or operation in the chat system.
 * </p>
 *
 * <p>Key responsibilities of this class:</p>
 * <ul>
 *   <li>Provide access to the message's content, sender, and timestamp.</li>
 *   <li>Define a common structure for all message types in the system.</li>
 *   <li>Offer a default string representation of the message for logging or display purposes.</li>
 * </ul>
 */
public abstract class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime timestamp;

    /**
     * Constructs a new Message with the specified content and sender.
     * The timestamp is set to the current time.
     * By default, sets the commandType to MESSAGE
     *
     *
     */
    public Message() {
        this.timestamp = LocalDateTime.now();
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
    /**
     * Returns a string representation of the message.
     *
     * @return a string representation of the message
     */
    @Override
    public String toString() {
        return getMessageAsString() + " from " + getSenderAsString() + " at " + timestamp;
    }

    protected String getMessageAsString(){
        return "Undefined message";
    }

    protected String getSenderAsString(){return "Undefined sender";}

    public void accept(ServerMessageVisitor handler) {
        System.out.println("Message accept method missing for " + this.getClass().getName());
    }
}
