package backend.Messages;

import backend.toLater.User;

public class LeaveChannelResponse extends Message{
    private String channelName;

    public LeaveChannelResponse(User user, String channelName){
        super(user);
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
