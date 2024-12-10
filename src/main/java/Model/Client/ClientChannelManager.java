package Model.Client;

import java.util.List;

import Model.Messages.UI.DisplayMessage;
import Model.Messages.UI.UIMessage;
import Model.Messages.UI.UpdateChannels;

public class ClientChannelManager {
    private final ClientChannelRecord channelRecord;
    private final List<ClientObserver> observers;

    public ClientChannelManager(ClientChannelRecord channelRecord, List<ClientObserver> observers) {
        this.channelRecord = channelRecord;
        this.observers = observers;
    }

    public void addNewChannel(String channelName) {
        channelRecord.addNewChannel(channelName);
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(), channelRecord.getCurrentChannelName()));
    }

    public void removeChannel(String channelName) {
        channelRecord.removeChannel(channelName);
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(), channelRecord.getCurrentChannelName()));
    }

    public void recordMessageInChannel(String sender, String message, String channelName) {
        channelRecord.recordMessageInChannel(sender, message, channelName);
    }

    public String getCurrentChannelName() {
        return channelRecord.getCurrentChannelName();
    }
    public void loadChatHistory(String channelName) {
        ClientChannel channel = channelRecord.getChannel(channelName);
        if (channel != null) {
            StringBuilder history = channel.getChatHistory();
            notifyObservers(new DisplayMessage("System", history.toString()));
        }
    }


    public void notifyObservers(UIMessage message) {
        for (ClientObserver observer : observers) {
            observer.update(message);
        }
    }
}


