package backend.Messages;

import backend.toLater.User;

public class JoinChannelResponse extends Message implements VisitableMessage {
    private String channelName;

    public JoinChannelResponse(String channelName){
        super("OK", "Server");
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public void accept(VisitorMessage visitor) {

    }
}
