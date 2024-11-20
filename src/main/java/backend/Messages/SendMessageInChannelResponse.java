package backend.Messages;

public class SendMessageInChannelResponse extends Message implements VisitableMessage {
    private String channelName;
    private String message;
    private String sender;

    public SendMessageInChannelResponse(String channelName, String message, String sender){
        super(message, sender);
        this.channelName = channelName;
        this.message = message;
        this.sender = sender;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public void accept(VisitorMessage visitor) {
        visitor.handle(this);
    }

}
