package backend.Messages.Client;

import backend.Messages.Message;
import backend.Messages.Server.ServerMessageVisitor;
import backend.Messages.Server.ServerVisitableMessage;

public class MessageInChannel extends Message implements ServerVisitableMessage {
    private String userName;
    private String channelName;
    private String message;

    public MessageInChannel(String userName, String channelName, String message){
        this.userName = userName;
        this.channelName = channelName;
        this.message = message;
    }

    public String getChannelName() {
        return channelName;
    }
    public String getMessage(){
        return message;
    }
    public String getUserName() {return userName;}

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.handle(this);
    }
}
