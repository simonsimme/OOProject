package Model.Messages.UI;

public class UIChannelHistory extends UIMessage {
    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}
