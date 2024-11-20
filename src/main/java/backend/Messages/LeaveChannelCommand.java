package backend.Messages;

import backend.toLater.User;

public class LeaveChannelCommand extends Message implements Visitable{
    private String channelName;

    public LeaveChannelCommand(User user, String channelName){
        super(user);
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
