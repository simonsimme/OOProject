package Model.Messages.UI;

public class DisplayChannelMessage implements UIMessage, UIVisitableMessage{
    private final String channelMessage;


    public DisplayChannelMessage(String channelMessage) {
        this.channelMessage = channelMessage;
    }
    public String getChannelMessage()
    {
        return channelMessage;
    }

    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}