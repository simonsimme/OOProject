package View.components.Decoraters;

import Model.EncryptionLayer;
import View.components.IView;
import Model.Messages.UI.*;

import javax.crypto.SecretKey;
import java.util.List;

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
            System.out.println(m.getMessage() + "-------");
            e.printStackTrace();
        }
        DisplayMessage dm = new DisplayMessage(m.getUserName(),ret, m.getChannelName());
    decoratedView.appendChatText(dm);
    }

    @Override
    public void handle(UpdateChannels u) {
        decoratedView.updateChannelList(u.getChannels(), u.getCurrentChannel());
    }

    //TODO implement this
    public void handle(UIChannelHistory m) {
        List<DisplayMessage> messages = m.parseHistory();
        decoratedView.clearChatText();
        for (DisplayMessage message : messages) {
            String ret = "";
            try
            {
                ret = EncryptionLayer.decrypt(message.getMessage(), key);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            DisplayMessage dm = new DisplayMessage(message.getUserName(),ret, "---");

            decoratedView.appendChatText(dm);
        }


    }

    @Override
    public void handle(DisplayChannelMessage displayChannelMessage) {
        DisplayMessage dm = new DisplayMessage("",displayChannelMessage.getChannelMessage(),"");
        decoratedView.appendChatText(dm);
    }
}
