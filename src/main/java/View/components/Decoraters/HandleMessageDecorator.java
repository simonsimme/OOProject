package View.components.Decoraters;

import Model.Messages.UI.*;
import View.components.IView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;




public class HandleMessageDecorator extends ViewDecorator implements UIMessageVisitor {
    public HandleMessageDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void handle(DisplayError e) {
    decoratedView.displayErrorMessage(e.getErrorMessage());
    }

    @Override
    public void handle(DisplayMessage m) {
        DisplayCode displayCode = new DisplayCode(m.getMessage());
        handle(displayCode);
    //decoratedView.appendChatText(m);
    }

    @Override
    public void handle(UpdateChannels u) {
        decoratedView.updateChannelList(u.getChannels(), u.getCurrentChannel());
    }

    @Override
    public void handle(DisplayCode displayCode) {

        decoratedView.appendChatText(displayCode);
    }
}
