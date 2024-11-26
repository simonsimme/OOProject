// DecryptionDecorator.java
package View;

import backend.Messages.*;


import java.util.Base64;

public class DecryptionDecorator extends ViewDecorator {

    public DecryptionDecorator(IView decoratedView) {
        super(decoratedView);
    }

    @Override
    public void appendChatText(Message text) {
       // String decryptedText = decrypt(text);
        super.appendChatText(text);
    }

    private String decrypt(String text) {

        return new String(Base64.getDecoder().decode(text));
    }
}