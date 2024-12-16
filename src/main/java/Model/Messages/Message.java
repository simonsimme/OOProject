package Model.Messages;
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
public interface Message extends Serializable {
    public String toString();
    //String getSenderAsString();

}
