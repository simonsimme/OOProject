package backend.Messages.UI;

import backend.Messages.Message;

public class DisplayMessage extends UIMessage {
    private String userName;
    private String message;

    public DisplayMessage(String userName, String message){
        this.userName = userName;
        this.message = message;
    }

    public String getUserName(){return userName;}
    public String getMessage() {
        return message;
    }

    @Override
    public void accept(UIMessageVisitor visitor) {
         visitor.handle(this);
    }

}
