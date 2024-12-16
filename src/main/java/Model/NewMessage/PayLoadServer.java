package Model.NewMessage;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PayLoadServer implements Sendabel, ServerSide, Serializable {

    MessageType type;
    String sender;
    String reciver;

    private final LocalDateTime timestamp;

    public PayLoadServer(String sender, String reciver, MessageType type){
        this.type = type;
        this.sender = sender;
        this.reciver = reciver;
        timestamp = LocalDateTime.now();
    }
    @Override
    public void accept() {

    }

    @Override
    public MessageType getType() {
        return null;
    }

    @Override
    public String toSting() {
        return "testing";
    }

    @Override
    public String content() {
        return this.type.getContent();
    }
}
