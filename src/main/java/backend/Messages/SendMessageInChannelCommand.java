package backend.Messages;

import backend.toLater.User;

public class SendMessageInChannelCommand extends Message implements Visitable {
    private String channelName;
    private String message;


    public SendMessageInChannelCommand(String sender, String channelName, String message){
        super(message, sender);
        this.channelName = channelName;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.handle(this);
    }

}
