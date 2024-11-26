package backend.Messages.Server;

import backend.Messages.Message;

public class CreateChannelCommand extends ServerMessage {
    private String userName;
    private String channelName;
    private String channelPassword;
    //TODO : Add channel settings ?

    public CreateChannelCommand(String userName,String channelName){
     this(userName,channelName,"");
    }

    public CreateChannelCommand(String userName, String channelName, String channelPassword){
        this.userName = userName;
        this.channelName = channelName;
        this.channelPassword = channelPassword;
    }

    public String getChannelPassword() {
        return channelPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void accept(ServerMessageVisitor serverMessageVisitor)
    {
        serverMessageVisitor.handle(this);
    }
}
