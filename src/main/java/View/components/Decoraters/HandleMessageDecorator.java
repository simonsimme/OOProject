package View.components.Decoraters;

import Model.EncryptionLayer;
import View.components.IView;
import Model.Messages.UI.*;

import javax.crypto.SecretKey;

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
        String ret = "ERORR";
        try
        {
            ret = EncryptionLayer.decrypt( m.getMessage(), key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        DisplayMessage dm = new DisplayMessage(m.getUserName(),ret);
    decoratedView.appendChatText(dm);
    }

    @Override
    public void handle(UpdateChannels u) {
        decoratedView.updateChannelList(u.getChannels(), u.getCurrentChannel());
    }

    //TODO implement this
    public void handle(UIChannelHistory message) {
        try {
            String history = message.getHistory();
            String parsedHistory = parseHistory(history);
            DisplayMessage dm = new DisplayMessage("Loading ChatHistory", parsedHistory);
            decoratedView.appendChatText(dm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String parseHistory(String history) throws Exception {
        StringBuilder historyString = new StringBuilder();
        historyString.append("\n");
        String[] lines = history.split(System.lineSeparator());
        for (String line : lines) {
            if (line.contains("Message:")) {
                String[] parts = line.split(" Message: ");
                if (parts.length == 2) {
                    String timestamp = parts[0].trim();
                    String[] messageParts = parts[1].split(" from ");
                    if (messageParts.length == 2) {
                        String encryptedMessage = messageParts[0].trim();
                        String sender = messageParts[1].trim();
                        try {
                            String decryptedMessage = EncryptionLayer.decrypt(encryptedMessage, key);
                            historyString.append(sender).append(" - ").append(decryptedMessage).append(System.lineSeparator());
                        } catch (Exception e) {
                            e.printStackTrace();
                            historyString.append(sender).append(" - ").append("ERROR: Unable to decrypt message").append(System.lineSeparator());
                        }
                    }
                }
            }
        }
        return historyString.toString();
    }
}

