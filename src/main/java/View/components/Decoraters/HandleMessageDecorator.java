package View.components.Decoraters;

import Model.EncryptionLayer;
import View.components.IView;
import Model.Messages.UI.*;

import javax.crypto.SecretKey;

public class HandleMessageDecorator extends ViewDecorator implements UIMessageVisitor {
    SecretKey key;
    public HandleMessageDecorator(IView decoratedView ,SecretKey key) {
        super(decoratedView);
        this.key = key;

    }

    @Override
    public void handle(DisplayError e) {
    decoratedView.displayErrorMessage(e.getErrorMessage());
    }

    @Override
    public void handle(DisplayMessage m) {
        String ret = "";
        try
        {
            ret = EncryptionLayer.decrypt( m.getMessage(), key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        DisplayMessage dm = new DisplayMessage(m.getUserName(),ret);
    decoratedView.appendChatText(dm);
    }

    @Override
    public void handle(UpdateChannels u) {
        decoratedView.updateChannelList(u.getChannels(), u.getCurrentChannel());
    }

    //TODO implement this
    public void handle(UIChannelHistory m) {
        String ret = "";
        try
        {
            ret = EncryptionLayer.decrypt(m.parseHistory().getMessage(), key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        DisplayMessage dm = new DisplayMessage(m.parseHistory().getUserName(),ret);
        decoratedView.appendChatText(dm);
    }
}
