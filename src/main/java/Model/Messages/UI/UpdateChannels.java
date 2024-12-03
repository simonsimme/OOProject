package Model.Messages.UI;

import java.util.List;

public class UpdateChannels extends UIMessage {
    private List<String> channels;
    private String current;

    public UpdateChannels(List<String> channels,String current){
        this.channels = channels;
        this.current = current;
    }

    public List<String> getChannels() {
        return channels;
    }
    public String getCurrentChannel() {
        return current;
    }

    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}
