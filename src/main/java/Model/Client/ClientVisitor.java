package Model.Client;

import Model.Messages.Client.*;
import Model.Messages.UI.DisplayError;
import Model.Messages.UI.DisplayMessage;
import Model.Messages.UI.UIMessage;
import Model.Messages.UI.UpdateChannels;

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
    private final ClientChannelManager channelManager;

    public  ClientVisitor(ClientChannelManager cm){
        this.channelManager = cm;
    }

    /**
     * Adds the newly joined channel to channelGroup and notifies the UI to update its channels with the new
     * information.
     * @param m
     */
    @Override
    public void handle(JoinChannelResponse m) {
        channelManager.addNewChannel(m.getChannelName());
        channelManager.loadChatHistory(m.getChannelName());
    }

    /**
     * Adds the new channel to channelGroup and notifies the UI to update its channels with the new information.
     * @param m
     */
    @Override
    public void handle(CreateChannelResponse m) {
        channelManager.addNewChannel(m.getChannelName());
    }

    /**
     * Removes the channel from channelGroup and notifies the UI to update its channels with the new information.
     * @param m
     */
    @Override
    public void handle(LeaveChannelResponse m)
    {
        channelManager.removeChannel(m.getChannelName());
    }

    /**
     * Notifies the UI to display an error description.
     * @param m
     */
    @Override
    public void handle(ErrorResponse m)
    {
        channelManager.notifyObservers(new DisplayError(m.getErrorMessage()));
    }

    /**
     * Handles the receiving of a message from a user in a channel. If the message is sent to the currently active
     * channel, the message is saved to the client's channel record(channelGroup.sendMessage(message,channel)) and
     * is displayed. However, if the message is sent to any other channel that is not currently active, the message
     * simply gets stored without being displayed.
     * @param m message
     */
    @Override
    public void handle(MessageInChannel m) {
        if(m.getChannelName().equals(channelManager.getCurrentChannelName())){
            channelManager.notifyObservers(new DisplayMessage(m.getUserName(),m.getMessage()));}
        channelManager.recordMessageInChannel(m.getUserName(), m.getMessage(),m.getChannelName());
    }

}
