package View;

import backend.Messages.UI.*;

public class ErrorMessageDecorator extends ViewDecorator implements UIMessageVisitor {
    public ErrorMessageDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void handle(DisplayError e) {

    }

    @Override
    public void handle(DisplayMessage m) {
        System.out.println("DisplayMessage: " + m.getUserName() + " ----" + m.getMessage());
    decoratedView.appendChatText(m);
    }

    @Override
    public void handle(UpdateChannels u) {

    }
}
