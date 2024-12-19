package Model.Client;

import Model.Messages.Client.*;
import Model.Messages.UI.*;
import java.util.List;

/**
 * ClientVisitor handles each different "request" sent to the client from the server.
 * The "request" comes in the explicit form of a ClientMessage. This class processes
 * the various types of messages and updates the client accordingly by modifying
 * the channel record and notifying observers.
 */
public class ClientVisitor implements ClientMessageVisitor{
    /**
     * The channelRecord is passed down to the ClientMessageVisitor from the Client class since ClientMessageVisitor
     * needs to manipulate it.
     */
    private final ClientChannelRecord channelRecord;
    /**
     * The list of observers observing the client is passed down to ClientMessageVisitor since ClientMessageVisitor
     * needs to notify them.
     */
    private final List<ClientObserver> observers;

    /**
     * Constructor for the ClientVisitor.
     * @param channelRecord A reference to the ClientChannelRecord that holds the channels.
     * @param observers A list of observers to be notified of changes.
     */
    public  ClientVisitor(ClientChannelRecord channelRecord, List<ClientObserver> observers){
        this.channelRecord = channelRecord;
        this.observers = observers;
    }

    /**
     * Handles the JoinChannelResponse message by adding the new channel to the channel record
     * and notifying observers to update the UI with the new channel information.
     * @param message The JoinChannelResponse message containing the channel name.
     */
    @Override
    public void handle(JoinChannelResponse message) {
        channelRecord.addNewChannel(message.getChannelName());
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(),channelRecord.getCurrentChannelName()));
    }

    /**
     * Handles the CreateChannelResponse message by adding the new channel to the channel record
     * and notifying observers to update the UI with the new channel information.
     * @param message The CreateChannelResponse message containing the channel name.
     */
    @Override
    public void handle(CreateChannelResponse message) {
        channelRecord.addNewChannel(message.getChannelName());
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(),channelRecord.getCurrentChannelName()));
    }

    /**
     * Handles the LeaveChannelResponse message by removing the specified channel from the channel record
     * and notifying observers to update the UI with the new channel information.
     * @param message The LeaveChannelResponse message containing the channel name to leave.
     */
    @Override
    public void handle(LeaveChannelResponse message) {
        channelRecord.removeChannel(message.getChannelName());
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(), channelRecord.getCurrentChannelName()));
    }

    /**
     * Handles the ErrorResponse message by notifying observers to display an error message in the UI.
     * @param message The ErrorResponse message containing the error description.
     */
    @Override
    public void handle(ErrorResponse message) {
        notifyObservers(new DisplayError(message.getErrorMessage()));
    }

    /**
     * Handles the MessageInChannel message by checking if the message belongs to the currently active channel.
     * If it does, the message is displayed. If the message is from another channel, it is stored without displaying.
     * The message is recorded in the channel history.
     * @param message The MessageInChannel containing the details of the message.
     */
    @Override
    public void handle(MessageInChannel message) {
        DisplayMessage displayMessage = new DisplayMessage(message.getUserName(), message.getMessage(), message.getChannelName());
        if(message.isServerMessage())
        {
            DisplayChannelMessage displayChannelMessage = new DisplayChannelMessage(message.getMessage());
            notifyObservers(displayChannelMessage);
        }
        else
        if (message.getChannelName().equals(channelRecord.getCurrentChannelName())) {
            notifyObservers(displayMessage);
        }else {
            notificationToClients(displayMessage);

        }
        channelRecord.recordMessageInChannel(message.getMessage(),message.getChannelName());
    }

    /**
     * Handles the RetrieveChatHistoryResponse message by updating the channel's chat history
     * and notifying observers to display the updated history in the UI.
     * @param message The RetrieveChatHistoryResponse containing the channel name and chat history.
     */
    @Override
    public void handle(RetrieveChatHistoryResponse message) {
        String channelName = message.getChannelName(); // get the channel name
        String history = message.toString(); // get the history
        channelRecord.setChannelHistory(channelName, new StringBuilder(history));
        notifyObservers(new UIChannelHistory(history));
    }

    /**
     * Notifies all registered observers of a UI message update.
     * @param message The UIMessage containing the update to notify observers about.
     */
    public void notifyObservers(UIMessage message) {
        for (ClientObserver observer: observers) {
            observer.update(message);
        }
    }

    /**
     * Sends a notification to all clients (observers) without displaying the message in the UI.
     * @param message The DisplayMessage to be notified to the clients.
     */
    private void notificationToClients(DisplayMessage message){
        for (ClientObserver observer:observers) {
            observer.notification(message);
        }
    }
}
