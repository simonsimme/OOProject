package Model.Messages.UI;

import java.util.ArrayList;
import java.util.List;

public class UIChannelHistory extends UIMessage {
    private String history;

    public UIChannelHistory(String history){
        this.history = history;
    }
    public String getHistory() {
        return history;
    }

    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }

    /**
     * Parses the history string and returns a list of DisplayMessages.
     * @return List of DisplayMessages.
     */

}
