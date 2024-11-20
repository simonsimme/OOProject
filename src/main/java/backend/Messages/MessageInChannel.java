package backend.Messages;

import backend.toLater.User;

public class MessageInChannel extends Message implements Visitable{
    private String channelName;
    private String message;

    public MessageInChannel(User user, String channelName, String message){
        super(user);
        this.channelName = channelName;
        this.message = message;
    }

    public String getChannelName() {
        return channelName;
    }
    public String getMessage(){
        return message;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.handle(this);
    }
}
