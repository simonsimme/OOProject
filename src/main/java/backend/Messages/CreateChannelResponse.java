package backend.Messages;

public class CreateChannelResponse extends Message implements VisitableMessage {
    private String channelName;
    //TODO : Add channel settings?

    // PARAMS CAN CHANGE WHEN IMPLEMENTED FUNCTIONALITY
    public CreateChannelResponse(String channelName){
        super("OK", "Server");
        this.channelName = channelName;
    }

    public String getChannelName(){
        return channelName;
    }

    @Override
    public void accept(VisitorMessage visitor) {

    }
}
