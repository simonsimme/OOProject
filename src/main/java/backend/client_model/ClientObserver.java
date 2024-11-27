package backend.client_model;

import backend.Messages.UI.UIMessage;

/**
 * Interface for the observer pattern. UIController implements this.
 */
public interface ClientObserver {
    void update(UIMessage message);
    void loadHistory(StringBuilder history);
}
