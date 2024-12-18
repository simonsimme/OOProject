package Model.Client;

import Model.Messages.Client.*;
import Model.Messages.UI.*;

import java.util.List;

/**
 * Client visitor handles each different "request" sent to the client from the server. The "request" comes in the
 * explicit form of a ClientMessage.
 */
public class ClientVisitor implements ClientMessageVisitor{
    /**
     * The channelRecord is passed down to the ClientMessageVisitor from the Client class since ClientMessageVisitor
     * needs to manipulate it.
     */
    private ClientChannelRecord channelRecord;
    /**
     * The list of observers observing the client is passed down to ClientMessageVisitor since ClientMessageVisitor
     * needs to notify them.
     */
    private List<ClientObserver> observers;

    /**
     * Constructor
     * @param channelGroup Passed down reference.
     * @param observers Passed down reference.
     */
    public  ClientVisitor(ClientChannelRecord channelGroup, List<ClientObserver> observers){
        this.channelRecord = channelGroup;
        this.observers = observers;
    }

    /**
     * Adds the newly joined channel to channelGroup and notifies the UI to update its channels with the new
     * information.
     * @param message
     */
    @Override
    public void handle(JoinChannelResponse message) {
        channelRecord.addNewChannel(message.getChannelName());
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(),channelRecord.getCurrentChannelName()));
    }

    /**
     * Adds the new channel to channelGroup and notifies the UI to update its channels with the new information.
     * @param message
     */
    @Override
    public void handle(CreateChannelResponse message) {
        channelRecord.addNewChannel(message.getChannelName());
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(),channelRecord.getCurrentChannelName()));
    }

    /**
     * Removes the channel from channelGroup and notifies the UI to update its channels with the new information.
     * @param message
     */
    @Override
    public void handle(LeaveChannelResponse message) {
        channelRecord.removeChannel(message.getChannelName());
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(), channelRecord.getCurrentChannelName()));
    }

    /**
     * Notifies the UI to display an error description.
     * @param m
     */
    @Override
    public void handle(ErrorResponse m) {
        notifyObservers(new DisplayError(m.getErrorMessage()));
    }

    /**
     * Handles the receiving of a message from a user in a channel. If the message is sent to the currently active
     * channel, the message is saved to the client's channel record(channelGroup.sendMessage(message,channel)) and
     * is displayed. However, if the message is sent to any other channel that is not currently active, the message
     * simply gets stored without being displayed.
     * @param message message
     */
    @Override
    public void handle(MessageInChannel message) {
        DisplayMessage dm = new DisplayMessage(message.getUserName(), message.getMessage(), message.getChannelName());

        if(message.isServerMessage())
        {
            DisplayChannelMessage displayChannelMessage = new DisplayChannelMessage(message.getMessage());
            notifyObservers(displayChannelMessage);
        }

        else
        if (message.getChannelName().equals(channelRecord.getCurrentChannelName())) {
            notifyObservers(dm);
        }else {
            notificationToClients(dm);

        }
        channelRecord.recordMessageInChannel(message.getMessage(),message.getChannelName());
    }

    @Override
    public void handle(RetrieveChatHistoryResponse message) {
        String channelName = message.getChannelName(); // get the channel name
        String history = message.toString(); // get the history
        channelRecord.setChannelHistory(channelName, new StringBuilder(history));
        notifyObservers(new UIChannelHistory(history));
    }

    /**
     * Notifies any listening observers (the UI)
     * @param message
     */
    public void notifyObservers(UIMessage message) {
        for (ClientObserver observer: observers) {
            observer.update(message);
        }
    }
    private void notificationToClients(DisplayMessage message){
        for (ClientObserver observer:observers) {
            observer.notification(message);
        }
    }
}
