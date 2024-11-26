package backend.Messages.Server;

import backend.Messages.Message;

public class LeaveChannelCommand extends ServerMessage {
    private String userName;
    private String channelName;

    public LeaveChannelCommand(String userName, String channelName){
        this.userName = userName;
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getUserName() {
        return userName;
    }

    public void accept(ServerMessageVisitor serverMessageVisitor)
    {
        serverMessageVisitor.handle(this);
    }
}
