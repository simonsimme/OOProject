package Controller;

import Model.Client.Client;
import Model.EncryptionLayer;
import Model.Messages.UI.DisplayMessage;
import Model.Messages.UI.UIMessage;
import Model.Client.ClientObserver;
import View.components.Decoraters.HandleMessageDecorator;
import View.components.IView;
import javax.crypto.SecretKey;

/**
 * The {@code UIClientObserver} class is an implementation of the {@link ClientObserver} interface.
 * It observes updates related to the client (such as receiving UI messages and notifications) and
 * updates the user interface accordingly.
 * <p>
 * This class interacts with the {@link IView} interface for displaying messages and notifications,
 * uses encryption to decrypt messages for notification, and utilizes a {@link HandleMessageDecorator}
 * to process and display UI messages.
 * </p>
 */
public class UIClientObserver implements ClientObserver {
    private final IView view;
    private final SecretKey key;
    private final HandleMessageDecorator handleMessageDecorator;
    private final Client reference;

    /**
     * Constructs a new {@code UIClientObserver} to observe client updates and interact with the view.
     *
     * @param view     the view interface to interact with the user interface
     * @param key      the secret key used for decrypting messages
     * @param reference the client instance being observed
     */
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

    /**
     * Displays a notification based on the received {@link DisplayMessage}.
     * This method decrypts the message and shows a notification if the message is not from the
     * current client or the current channel. It uses the provided secret key for decryption.
     * @param message the {@link DisplayMessage} to decrypt and display as a notification
     */
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
}
