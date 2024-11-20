package backend.Messages;

public class SendMessageInChannelCommand implements Visitable {
    private String channelName;
    private String message;

    public SendMessageInChannelCommand(User user,){

    }

    public void accept(Visitor visitor) {

    }
}
