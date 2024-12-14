package Model.Messages.Client;

public class RetrieveChatHistoryResponse extends ClientMessage {

    private String channelName;
    private StringBuilder history;

    public RetrieveChatHistoryResponse(String channelName, StringBuilder history){
        this.channelName = channelName;
        this.history = history;
    }

    public String getChannelName() {
        return channelName;
    }
    @Override
    public String toString() {
        return "RetrieveChatHistoryResponse{" +
                "channelName='" + channelName + '\'' +
                ", history=" + history +
                '}';
    }

    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }

    public StringBuilder getStringBuilder() {
        return history;
    }
}
