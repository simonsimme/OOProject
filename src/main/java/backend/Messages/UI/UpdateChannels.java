package backend.Messages.UI;

import java.util.List;

public class UpdateChannels extends UIMessage {
    private List<String> channels;
    private String current;

    public UpdateChannels(List<String> channels,String current){
        this.channels = channels;
        this.current = current;
    }

    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}
