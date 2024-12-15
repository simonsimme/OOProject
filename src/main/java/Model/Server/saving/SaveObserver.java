package Model.Server.saving;

import Model.Messages.Message;

public interface SaveObserver {
    //void update(String userName, String channelName, String message);

    void update(String name, Message message);
}