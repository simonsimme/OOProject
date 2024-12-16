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

        try
        {
            String retText = EncryptionLayer.decrypt( m.getMessage(), key);
            String retName = EncryptionLayer.decrypt( m.getUserName(), key);
            DisplayMessage dm = new DisplayMessage(retName,retText, m.getChannelName());
            decoratedView.appendChatText(dm);
        }
        catch (Exception e)
        {
            System.out.println(m.getMessage() + "-------");
            e.printStackTrace();
        }

    }

    @Override
    public void handle(UpdateChannels u) {
        decoratedView.updateChannelList(u.getChannels(), u.getCurrentChannel());
    }

    public void handle(UIChannelHistory m) {
        List<DisplayMessage> messages = m.parseHistory();
        decoratedView.clearChatText();
        for (DisplayMessage message : messages) {
            try
            {
                String retText = EncryptionLayer.decrypt( message.getMessage(), key);
                String retName = EncryptionLayer.decrypt( message.getUserName(), key);
                DisplayMessage dm = new DisplayMessage(retName,retText, message.getChannelName());
                decoratedView.appendChatText(dm);
            }
            catch (Exception e)
            {
                System.out.println("Error in loading history");
            }

        }


    }

    @Override
    public void handle(DisplayChannelMessage displayChannelMessage) {
        DisplayMessage dm = new DisplayMessage("",displayChannelMessage.getChannelMessage(),"");
        decoratedView.appendChatText(dm);
    }
}
