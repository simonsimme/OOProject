package Model.Messages.UI;

public class DisplayChannelMessage extends UIMessage{
    private final String channelMessage;


    public DisplayChannelMessage(String channelMessage) {
        this.channelMessage = channelMessage;
    }
    public String getChannelMessage()
    {
        return channelMessage;
    }
    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}