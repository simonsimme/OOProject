package backend.Messages.Client;

public class JoinChannelResponse extends ClientMessage {
    private String channelName;


    public JoinChannelResponse(String channelName){
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public void accept(ClientMessageVisitor visitor) {

    }
}
