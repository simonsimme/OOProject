package Controller;

import Model.Messages.UI.UIMessage;
import Model.Client.ClientObserver;
import View.components.Decoraters.HandleMessageDecorator;
import View.components.IView;

import javax.crypto.SecretKey;

public class UIClientObserver implements ClientObserver {
    private final IView view;

    private final HandleMessageDecorator handleMessageDecorator;
    public UIClientObserver(IView view, SecretKey key) {
        this.view = view;
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
     * Loads the chat history into the view.
     * @param history the chat history to load.
     */
    @Override
    public void loadHistory(StringBuilder history) {
        view.showHistory(history);
    }

}
