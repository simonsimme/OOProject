package Model.Messages.Client;

public class RetrieveChatHistoryResponse extends ClientMessage {

    private String channelName;
    private StringBuilder text;

    public RetrieveChatHistoryResponse(String channelName){
        this.channelName = channelName;
        this.text = new StringBuilder();
    }

    public String getChannelName() {
        return channelName;
    }

    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
