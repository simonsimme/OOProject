package backend.Messages.Server;

import backend.Messages.Message;

public class JoinChannelCommand extends ServerMessage {
    private String userName;
    private String channelName;
    private String password;

    public JoinChannelCommand(String userName, String channelName, String password){
        this.userName = userName;
        this.channelName = channelName;
        this.password = password;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public void accept(ServerMessageVisitor serverMessageVisitor)
    {
        serverMessageVisitor.handle(this);
    }
}
