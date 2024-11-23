package backend.Messages.UI;
import View.View;
public class MessageVisitorUI implements UIMessageVisitor {
    private View view;
    public MessageVisitorUI(View view){
        this.view = view;
    }

    @Override
    public void handle(DisplayError e) {
        view.appendChatText("Error: DisplarError");
    }

    @Override
    public void handle(DisplayMessage m) {
        System.out.println("DisplayMessage: " + m.getUserName() + " " + m.getMessage());
        view.appendChatText(m.getTimestamp().getHour() + ":" + m.getTimestamp().getMinute() + " " + m.getUserName() + ": " + m.getMessage());
    }

    @Override
    public void handle(UpdateChannels u) {
        view.updateChannelList(u.getChannels(), u.getCurrentChannel());
    }
}
