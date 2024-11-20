package backend.client_model;

import backend.Message;

import java.util.Observer;

public interface ClientSubject {
    void attach(ClientObserver observer);
    void detach(ClientObserver observer);
    void notifyObservers(Message message);
}
