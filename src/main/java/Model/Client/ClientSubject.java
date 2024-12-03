package Model.Client;

import Model.Messages.UI.UIMessage;

/**
 * Interface for the observer pattern. Client implements this.
 */
public interface ClientSubject {
    void attach(ClientObserver observer);
    void detach(ClientObserver observer);
    void notifyObservers(UIMessage message);
}
