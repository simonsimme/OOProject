package Model.Messages.Client;

public class RetrieveChatHistoryResponse extends ClientMessage {

    private String channelName;
    private StringBuilder history;

    public RetrieveChatHistoryResponse(String channelName, StringBuilder history){
        this.channelName = channelName;
        this.history = new StringBuilder();
    }

    public String getChannelName() {
        return channelName;
    }

    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
