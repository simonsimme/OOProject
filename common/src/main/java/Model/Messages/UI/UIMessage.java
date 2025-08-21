package Model.Messages.UI;

import Model.Messages.Message;

/**
 * Interface for all UI messages. This is used between the client and view.
 */
public interface UIMessage extends Message {
    void accept(UIMessageVisitor visitor);
}
