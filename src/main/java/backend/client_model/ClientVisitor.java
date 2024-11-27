package backend.client_model;

import backend.Messages.Client.*;
import backend.Messages.UI.DisplayError;
import backend.Messages.UI.DisplayMessage;
import backend.Messages.UI.UIMessage;
import backend.Messages.UI.UpdateChannels;

import java.util.List;

/**
 * Client visitor handles each different "request" sent to the client from the server. The "request" comes in the
 * explicit form of a ClientMessage.
 */
public class ClientVisitor implements ClientMessageVisitor{
    /**
     * The channelGroup is passed down to the ClientMessageVisitor from the Client class since ClientMessageVisitor
     * needs to manipulate it.
     */
    private ClientChannelRecord channelGroup;
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
        this.channelGroup = channelGroup;
        this.observers = observers;
    }

    /**
     * Adds the newly joined channel to channelGroup and notifies the UI to update its channels with the new
     * information.
     * @param m
     */
    @Override
    public void handle(JoinChannelResponse m) {
        channelGroup.addNewChannel(m.getChannelName());
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }

    /**
     * Adds the new channel to channelGroup and notifies the UI to update its channels with the new information.
     * @param m
     */
    @Override
    public void handle(CreateChannelResponse m) {
        channelGroup.addNewChannel(m.getChannelName());
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }

    /**
     * Removes the channel from channelGroup and notifies the UI to update its channels with the new information.
     * @param m
     */
    @Override
    public void handle(LeaveChannelResponse m) {
        channelGroup.removeChannel(m.getChannelName());
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }

    /**
     *
     * @param m
     */
    @Override
    public void handle(SendMessageInChannelResponseClient m) {
        //Kanske inte behöver hantera detta...
    }

    /**
     * Notifies the UI to display the error description
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
     * @param m message
     */
    @Override
    public void handle(MessageInChannel m) { // Denna får vi fixa
        if(m.getChannelName() == channelGroup.getCurrentChannel().getChannelName()){
            notifyObservers(new DisplayMessage(m.getUserName(),m.getMessage()));
        }
        notifyObservers(new DisplayMessage(m.getUserName(), m.getMessage()));
        channelGroup.recordMessageInChannel(m.getMessage(),m.getChannelName());
    }

    /**
     * Notifies any listening observers (the UI)
     * @param message
     */
    public void notifyObservers(UIMessage message) {
        for (ClientObserver observer:observers) {
            observer.update(message);
        }
    }
}
