package Controller;

import Model.Client.Client;
import Model.EncryptionLayer;
import Model.Messages.UI.DisplayMessage;
import Model.Messages.UI.UIMessage;
import Model.Client.ClientObserver;
import View.components.Decoraters.HandleMessageDecorator;
import View.components.IView;

import javax.crypto.SecretKey;

public class UIClientObserver implements ClientObserver {
    private final IView view;
    private final SecretKey key;
    private final HandleMessageDecorator handleMessageDecorator;
    private final Client reference;
    public UIClientObserver(IView view, SecretKey key, Client reference) {
        this.view = view;
        this.key = key;
        this.reference = reference;
        this.handleMessageDecorator = new HandleMessageDecorator(view,key);
    }

    /**
     * Updates the UI based on the received UIMessage.
     * @param message the UIMessage to process.
     */
    @Override
    public void update(UIMessage message) {
        message.accept(handleMessageDecorator);
    }

    @Override
    public void nofitication(DisplayMessage message) {
        try {
            String msg = EncryptionLayer.decrypt(message.getMessage(),key);
            if (!message.getUserName().equals(reference.getUserName()) &&  !reference.getCurrentChannelName().equals(message.getChannelName())) {
                view.showNotification("Message from " + message.getUserName() + " in " + message.getChannelName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the chat history into the view.
     * @param history the chat history to load.
     */


}
