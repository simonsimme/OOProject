package backend.Messages;

import backend.toLater.User;

public class LeaveChannelResponse extends Message implements VisitableMessage {
    private String channelName;

    public LeaveChannelResponse(User user, String channelName){
        super("OK", user);
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public void accept(VisitorMessage visitor) {
        visitor.handle(this);
    }
}
