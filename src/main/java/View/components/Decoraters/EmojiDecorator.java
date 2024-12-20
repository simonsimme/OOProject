// EmojiDecorator.java
package View.components.Decoraters;

import Model.Messages.UI.DisplayMessage;
import View.components.IView;

/**
 * The {@code EmojiDecorator} class is a concrete implementation of the {@link ViewDecorator} class
 * that adds emoji conversion functionality to a decorated {@link IView} object.
 * <p>
 * This decorator intercepts the chat text before it is appended and converts emoticons (e.g., ":)" and ":(")
 * into corresponding emoji characters (e.g., "😊" and "😢").
 * </p>
 */
public class EmojiDecorator extends ViewDecorator {

    /**
     * Constructs an {@code EmojiDecorator} with the specified decorated view.
     *
     * @param decoratedView the {@link IView} object to be decorated with emoji conversion functionality.
     */
    public EmojiDecorator(IView decoratedView) {
        super(decoratedView);
    }

    /**
     * Appends chat text to the decorated view after converting emoticons to emojis.
     * <p>
     * This method overrides the {@code appendChatText} method of the {@link ViewDecorator} class
     * and converts emoticons (e.g., ":)" and ":(") into emojis (e.g., "😊" and "😢") before delegating
     * to the wrapped view's {@code appendChatText} method.
     * </p>
     *
     * @param text the {@link DisplayMessage} containing the chat message to be appended.
     */
    @Override
    public void appendChatText(DisplayMessage text) {
        DisplayMessage emojiText = convertEmoticonsToEmojis(text);
        super.appendChatText(emojiText);
    }

    /**
     * Converts emoticons in the message to corresponding emojis.
     * <p>
     * This method replaces common emoticons like ":)" with "😊" and ":(" with "😢" in the message text.
     * </p>
     *
     * @param text the {@link DisplayMessage} containing the original message.
     * @return a new {@link DisplayMessage} with the converted emoji message.
     */
    private DisplayMessage convertEmoticonsToEmojis(DisplayMessage text) {
        String messageWithEmojis = text.getMessage().replaceAll(":\\)", "😊").replaceAll(":\\(", "😢");
        return new DisplayMessage(text.getUserName(), messageWithEmojis, text.getChannelName());
    }
}