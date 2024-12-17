package Model.Messages.Server;

public class RetrieveChatHistoryCommand implements ServerMessage, ServerVisitableMessage {

    private String channelName;
    private String userName;

    public RetrieveChatHistoryCommand(String userName, String channelName){
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
