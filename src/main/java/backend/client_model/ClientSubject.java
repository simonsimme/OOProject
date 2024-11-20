package backend.client_model;

public interface ClientSubject {
    void attach(ClientObserver observer);
    void detach(ClientObserver observer);
    void notifyObservers(Message message);
}
