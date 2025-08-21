package View.components.Decoraters;

import Model.EncryptionLayer;
import Model.Messages.UI.*;
import View.components.IView;

import javax.crypto.SecretKey;
import java.util.List;

/**
 * The {@code HandleMessageDecorator} class is a concrete implementation of the {@link ViewDecorator} class
 * that handles the decryption and display of various types of UI messages.
 * <p>
 * This decorator intercepts incoming messages, decrypts them using a provided key, and passes them to the decorated view
 * for display. It also handles updates to the channel list and chat history.
 * </p>
 */
public class HandleMessageDecorator extends ViewDecorator implements UIMessageVisitor {
    private final SecretKey key;

    /**
     * Constructs a {@code HandleMessageDecorator} with the specified decorated view and encryption key.
     *
     * @param decoratedView the {@link IView} object to be decorated with message handling functionality.
     * @param key the {@link SecretKey} used for decrypting the messages.
     */
    public HandleMessageDecorator(IView decoratedView ,SecretKey key) {
        super(decoratedView);
        this.key = key;

    }

    /**
     * Handles a {@link DisplayError} message by passing the error message to the decorated view's error handling method.
     *
     * @param e the {@link DisplayError} containing the error message to be displayed.
     */
    @Override
    public void handle(DisplayError e) {
    decoratedView.displayErrorMessage(e.getErrorMessage());
    }

    /**
     * Handles a {@link DisplayMessage} by decrypting its content and passing it to the decorated view.
     * <p>
     * This method decrypts both the message and the username using the provided {@link SecretKey}.
     * After decryption, the message is passed to the decorated view for display.
     * </p>
     *
     * @param m the {@link DisplayMessage} containing the encrypted message content.
     */
    @Override
    public void handle(DisplayMessage m) {

        try
        {
            String retText = EncryptionLayer.decrypt( m.getMessage(), key);
            String retName = EncryptionLayer.decrypt( m.getUserName(), key);
            DisplayMessage dm = new DisplayMessage(retName,retText, m.getChannelName());
            dm.setTimestamp(m.getTimestamp());
            decoratedView.appendChatText(dm);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Handles an {@link UpdateChannels} message by updating the channel list in the decorated view.
     *
     * @param u the {@link UpdateChannels} message containing the updated channels and the current channel.
     */
    @Override
    public void handle(UpdateChannels u) {
        decoratedView.updateChannelList(u.getChannels(), u.getCurrentChannel());
    }

    /**
     * Handles a {@link UIChannelHistory} message by decrypting and displaying the channel's message history.
     * <p>
     * This method clears the current chat text and appends the decrypted history messages to the view.
     * </p>
     *
     * @param m the {@link UIChannelHistory} message containing the channel's message history.
     */
    public void handle(UIChannelHistory m) {
        List<DisplayMessage> messages = m.parseHistory();
        decoratedView.clearChatText();
        for (DisplayMessage message : messages) {
            try
            {
                String retText = EncryptionLayer.decrypt( message.getMessage(), key);
                String retName = EncryptionLayer.decrypt( message.getUserName(), key);
                DisplayMessage dm = new DisplayMessage(retName,retText, message.getChannelName());
                dm.setTimestamp(message.getTimestamp());
                decoratedView.appendChatText(dm);
            }
            catch (Exception e)
            {
                System.out.println("Error in loading history" + e);
            }
        }
    }

    /**
     * Handles a {@link DisplayChannelMessage} by displaying the channel message in the view.
     *
     * @param displayChannelMessage the {@link DisplayChannelMessage} containing the message to be displayed.
     */
    public void handle(DisplayChannelMessage displayChannelMessage) {
        DisplayMessage dm = new DisplayMessage("",displayChannelMessage.getChannelMessage(),"");
        dm.setTimestamp(displayChannelMessage.getTime());
        decoratedView.appendChatText(dm);
    }
}
