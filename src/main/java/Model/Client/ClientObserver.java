package Model.Client;

import Model.Messages.UI.DisplayMessage;
import Model.Messages.UI.UIMessage;

/**
 * Interface for the observer pattern. UIController implements this.
 */
public interface ClientObserver {
    void update(UIMessage message);
    void notification(DisplayMessage message);
}
