package backend.client_model;

import backend.Messages.Client.*;
import backend.Messages.UI.DisplayError;
import backend.Messages.UI.DisplayMessage;
import backend.Messages.UI.UIMessage;
import backend.Messages.UI.UpdateChannels;

import java.util.List;

public class ClientVisitor implements ClientMessageVisitor{
    private ClientChannelGroup channelGroup;
    private List<ClientObserver> observers;


    public  ClientVisitor(ClientChannelGroup channelGroup,List<ClientObserver> observers){
        this.channelGroup = channelGroup;
        this.observers = observers;
    }

    @Override
    public void handle(JoinChannelResponse m) {
        channelGroup.addNewChannel(m.getChannelName());
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }

    @Override
    public void handle(CreateChannelResponse m) {
        channelGroup.addNewChannel(m.getChannelName());
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }

    @Override
    public void handle(LeaveChannelResponse m) {
        channelGroup.removeChannel(m.getChannelName());
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }

    @Override
    public void handle(SendMessageInChannelResponseClient m) {
        //Kanske inte behöver hantera detta...
    }

    @Override
    public void handle(ErrorResponse m) {
        notifyObservers(new DisplayError(m.getErrorMessage()));
    }

    @Override
    public void handle(MessageInChannel m) {
        if(m.getChannelName() == channelGroup.getCurrentChannel().getChannelName()){
            notifyObservers(new DisplayMessage(m.getUserName(),m.getMessage()));
        }
         channelGroup.sendMessageInChannel(m.getMessage(),m.getChannelName());
    }

    public void notifyObservers(UIMessage message) {
        for (ClientObserver observer:observers) {
            observer.update(message);
        }
    }
}
