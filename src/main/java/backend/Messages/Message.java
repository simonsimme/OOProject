package backend.Messages;

import backend.toLater.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Message implements Serializable {
    private LocalDateTime timeStamp;
    User sender;

    public Message(User sender){
        this.sender = sender;
        this.timeStamp = LocalDateTime.now();
    }

    public User getSender() {
        return sender;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
