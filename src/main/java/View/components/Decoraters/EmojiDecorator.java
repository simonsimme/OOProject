// EmojiDecorator.java
package View.components.Decoraters;

import Model.Messages.UI.DisplayMessage;
import View.components.IView;

public class EmojiDecorator extends ViewDecorator {

    public EmojiDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void appendChatText(DisplayMessage text) {
        DisplayMessage emojiText = convertEmoticonsToEmojis(text);
        super.appendChatText(emojiText);
    }

    private DisplayMessage convertEmoticonsToEmojis(DisplayMessage text) {
        String messageWithEmojis = text.getMessage().replaceAll(":\\)", "😊").replaceAll(":\\(", "😢");
        return new DisplayMessage(text.getUserName(), messageWithEmojis, text.getChannelName());
    }
}