package Model.Messages.UI;

import Model.Messages.Message;

public abstract class UIMessage extends Message implements UIVisitableMessage{
    public String getContent() {
        return super.getMessageAsString();
    }

}
