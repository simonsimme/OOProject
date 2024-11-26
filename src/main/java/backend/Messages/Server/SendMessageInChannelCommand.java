package backend.Messages.Server;

import backend.Messages.Message;

public class SendMessageInChannelCommand extends ServerMessage {
    private final String userName;
    private final String channelName;
    private final String message;


    public SendMessageInChannelCommand(String userName, String channelName, String message){
        this.userName = userName;
        this.channelName = channelName;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.handle(this);
    }

}
