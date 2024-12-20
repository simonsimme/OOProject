package Model.Server.saving;

import Model.Messages.Message;

/**
 * The {@code SaveObserver} interface defines the contract for observers that
 * listen to new messages and perform an action, such as saving the message.
 * Implementing classes will handle the logic for updating and storing the received messages.
 */
public interface SaveObserver {

    /**
     * Updates the observer with a new message.
     * This method is called when a new message is available, and the observer
     * can then process it (e.g., save it to a file or perform other actions).
     *
     * @param message the {@link Message} to be processed by the observer
     */
    void update(Message message);
}