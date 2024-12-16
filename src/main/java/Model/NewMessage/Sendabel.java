package Model.NewMessage;

import java.time.LocalDateTime;

public interface Sendabel {
    void accept();
     MessageType getType();
     String toSting();

     String content();
}
