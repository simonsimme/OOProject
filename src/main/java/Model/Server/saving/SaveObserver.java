package Model.Server.saving;

public interface SaveObserver {
    void update(String userName, String channelName, String message);
}