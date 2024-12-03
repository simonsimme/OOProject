package Model.Server.saving;

public interface Subject {
    void registerObserver(SaveObserver observer);
    void removeObserver(SaveObserver observer);
    void notifyObservers(String userName, String channelName, String message);
}