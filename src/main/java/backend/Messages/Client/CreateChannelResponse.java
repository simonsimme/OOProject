package backend.Messages.Client;

public class CreateChannelResponse extends ClientMessage {
    private String channelName;
    //TODO : Add channel settings?

    // PARAMS CAN CHANGE WHEN IMPLEMENTED FUNCTIONALITY
    public CreateChannelResponse(String channelName){
        this.channelName = channelName;
    }

    public String getChannelName(){
        return channelName;
    }

    @Override
    public void accept(ClientMessageVisitor visitor) {

    }
}
