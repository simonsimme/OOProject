package Model.Messages.UI;

import java.util.List;

public class UpdateUserMessage extends UIMessage {
    private final List<String> users;

    public UpdateUserMessage(List<String> users) {
        this.users = users;
    }
    public List<String> getUsers() {
        return users;
    }
    @Override
    public void accept(UIMessageVisitor visitor) {

    }
}
