package Model.Messages.UI;

public class DisplayMessage extends UIMessage {
    private String userName;
    private String message;
    private final String channelName;

    public DisplayMessage(String userName, String message, String channelName){
        this.userName = userName;
        this.message = message;
        this.channelName = channelName;
    }
    public String getChannelName() {
        return channelName; // Add this getter
    }

    public String getUserName(){return userName;}
    public String getMessage() {
        return message;
    }

    @Override
    public void accept(UIMessageVisitor visitor) {
         visitor.handle(this);
    }

}
