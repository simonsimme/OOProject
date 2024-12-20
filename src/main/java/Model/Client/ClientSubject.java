package Model.Client;

import Model.Messages.UI.UIMessage;

/**
 * The {@code ClientSubject} interface defines the contract for objects that can be observed by {@code ClientObserver}s.
 * It is part of the observer design pattern, where the client (subject) notifies its observers of changes or events.
 * Classes implementing this interface manage the list of observers and handle the notification process.
 */
public interface ClientSubject {
    /**
     * Attaches an observer to the client.
     * The observer will be notified when there are updates or events that need to be communicated.
     *
     * @param observer the {@code ClientObserver} to be attached to the subject
     */
    void attach(ClientObserver observer);

    /**
     * Detaches an observer from the client.
     * The observer will no longer be notified when updates or events occur.
     *
     * @param observer the {@code ClientObserver} to be detached from the subject
     */
    void detach(ClientObserver observer);

    /**
     * Notifies all attached observers of an update.
     * This method sends a message to all observers, alerting them of changes or new information.
     *
     * @param message the {@code UIMessage} containing the update to be sent to observers
     */
    void notifyObservers(UIMessage message);
}
