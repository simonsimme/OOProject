package Model;
import Model.Messages.Server.ServerMessageVisitor;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The {@code Message} class represents an abstract base for messages exchanged
 * within a chat system. Messages encapsulate a timestamp indicating when they
 * were created and are designed to be extended by subclasses to support various
 * types of communication.
 *
 * <p>Each {@code Message} contains:</p>
 * <ul>
 *   <li>A timestamp representing the creation time of the message.</li>
 *   <li>Abstract methods to represent the message content and sender,
 *       which must be implemented by subclasses.</li>
 * </ul>
 *
 * <p>Key responsibilities of this class:</p>
 * <ul>
 *   <li>Provide a base structure for all types of messages in the chat system.</li>
 *   <li>Offer methods to retrieve message metadata, such as the creation timestamp.</li>
 *   <li>Include a default implementation for string representation,
 *       which can be overridden by subclasses.</li>
 *   <li>Allow the message to accept a visitor for processing,
 *       as part of the visitor design pattern.</li>
 * </ul>
 */
public abstract class Message implements Serializable {
    /**
     * Unique identifier for the {@code Message} class to support serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The timestamp indicating when the message was created.
     */
    private final LocalDateTime timestamp;
    /**
     * Constructs a new {@code Message} instance with the current time as the timestamp.
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
     * Provides a string representation of the message, including the content, sender,
     * and timestamp. Subclasses may override this method to customize the output.
     *
     * @return a string representation of the message
     */
    @Override
    public String toString() {
        return getMessageAsString() + " from " + getSenderAsString() + " at " + timestamp;
    }
    /**
     * Returns a string representation of the message content.
     * Subclasses must override this method to provide specific content.
     *
     * @return the message content as a string
     */
    protected String getMessageAsString(){
        return "Undefined message";
    }
    /**
     * Returns a string representation of the message sender.
     * Subclasses must override this method to provide sender information.
     *
     * @return the sender as a string
     */
    protected String getSenderAsString() { return "Undefined sender";}
    /**
     * Accepts a visitor for processing this message.
     * Subclasses may override this method to implement specific visitor logic.
     *
     * @param handler the {@link ServerMessageVisitor} to handle this message
     */
    public void accept(ServerMessageVisitor handler) {
        System.out.println("Message accept method missing for " + this.getClass().getName());
    }
}
