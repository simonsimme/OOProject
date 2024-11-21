package backend.Messages.Client;

public class LeaveChannelResponse extends ClientMessage {
    private String channelName;

    public LeaveChannelResponse(String channelName){
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
