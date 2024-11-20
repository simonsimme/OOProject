package backend.Messages;

import backend.toLater.User;

public class JoinChannelResponse extends Message {
    private String channelName;

    public JoinChannelResponse(User user, String channelName){
        super(user);
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
