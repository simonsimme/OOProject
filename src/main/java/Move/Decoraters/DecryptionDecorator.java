// DecryptionDecorator.java
package Move.Decoraters;

import Move.IView;
import Model.Messages.UI.DisplayMessage;


import java.util.Base64;

public class DecryptionDecorator extends ViewDecorator {

    public DecryptionDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void appendChatText(DisplayMessage text) {
       // String decryptedText = decrypt(text);
        super.appendChatText(text);
    }

    private String decrypt(String text) {

        return new String(Base64.getDecoder().decode(text));
    }
}