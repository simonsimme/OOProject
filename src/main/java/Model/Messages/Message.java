package Model.Messages;


import java.io.Serializable;

/**
 * Interface that extends Serializable. Used for all messages sent between client, server and UI.
 */
public interface Message extends Serializable {
     String toString();
}
