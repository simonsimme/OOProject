package Move.Decoraters;

import Move.IView;
import Model.Messages.UI.*;

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
    decoratedView.appendChatText(m);
    }

    @Override
    public void handle(UpdateChannels u) {
        decoratedView.updateChannelList(u.getChannels(), u.getCurrentChannel());
    }
}
