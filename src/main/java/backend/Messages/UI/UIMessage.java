package backend.Messages.UI;

import backend.Messages.Message;

public abstract class UIMessage extends Message implements UIVisitableMessage{
    public String getContent() {
        return super.getMessageAsString();
    }

}
