package backend.Messages;

public class CreateChannelResponse extends Message{
    private String channelName;
    //TODO : Add channel settings?

    public CreateChannelResponse(String channelName){
        this.channelName = channelName;
    }

    public String getChannelName(){
        return channelName;
    }
}
