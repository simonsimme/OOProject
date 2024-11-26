package backend.client_model;

import backend.Messages.UI.UIMessage;

public interface ClientObserver {
    void update(UIMessage message);
    void loadHistory(StringBuilder history);
}
