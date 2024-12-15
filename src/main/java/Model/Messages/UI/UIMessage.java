package Model.Messages.UI;

import Model.Messages.Message;

/**
 * Abstract class for all UI messages. This is used between the client and view.
 */
public abstract class UIMessage extends Message implements UIVisitableMessage{
    public String getContent() {
        return super.getMessageAsString();
    }

}
