package Model.NewMessage;

public class TextMessage implements MessageType{

    String content;

    TextMessage(String content){
        this.content = content;
    }

    @Override
    public String getContent() {
        return null;
    }
}
