package Model.Server.saving;

import Model.Messages.Message;

public interface SaveObserver {

    void update(Message message);
}