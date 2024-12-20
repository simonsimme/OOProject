package Model.Client;

import Model.Messages.UI.DisplayMessage;
import Model.Messages.UI.UIMessage;

/**
 * The {@code ClientObserver} interface defines the contract for objects that observe and react to updates from the {@code Client}.
 * It is part of the observer design pattern. Implementing classes, like {@code UIController}, are notified of new messages and notifications.
 */
public interface ClientObserver {

    /**
     * Updates the observer with a new user interface (UI) message.
     * This method is called when a new message needs to be displayed or processed.
     *
     * @param message the UI message containing the data to be displayed or processed
     */
    void update(UIMessage message);

    /**
     * Sends a notification to the observer.
     * This method is called when a notification needs to be displayed, such as alerts or status updates.
     *
     * @param message the display message containing the notification data
     */
    void notification(DisplayMessage message);
}
