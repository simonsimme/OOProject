package Model.Messages.UI;
import View.components.IView;
public class MessageVisitorUI implements UIMessageVisitor {
    private IView view;
    public MessageVisitorUI(IView view){
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
        //view.updateChannelList(u.getChannels(), u.getCurrentChannel()); // this adds in channels from getChannels() , kolla med Simon innan lägga tillbacka
    }

    @Override
    public void handle(DisplayCode displayCode) {

    }
}
