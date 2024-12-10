package Model.Messages.Server;

public class RetrieveChatHistoryRequest extends ServerMessage {

    private String channelName;
    private String userName;

    public RetrieveChatHistoryRequest(String userName, String channelName){
        this.channelName = channelName;
        this.userName = userName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void accept(ServerMessageVisitor visitor) {
        visitor.handle(this);
    }
}
