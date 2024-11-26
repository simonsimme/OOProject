package backend.Messages.Client;

public class SendMessageInChannelResponseClient extends ClientMessage  {
    private String channelName;
    private String message;
    private String userName;

    public SendMessageInChannelResponseClient(String userName,String channelName, String message){
        this.userName = userName;
        this.channelName = channelName;
        this.message = message;
    }

    public String getChannelName() {
        return channelName;
    }
    public String getUserName(){return userName;}
    public String getMessage() {
        return message;
    }

    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }


}
