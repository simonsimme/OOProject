package backend.client_model;

import backend.Message;

public interface ClientObserver {
    void update(Message message);
}
