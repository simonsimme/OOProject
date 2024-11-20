package backend.Messages;

import backend.ClientHandler;

public class ServerMessageVisitor implements Visitor {
    private ClientHandler ch;

    public ServerMessageVisitor(ClientHandler clientHandler)
    {
        this.ch = clientHandler;
    }

    @Override
    public void handle(LeaveChannelCommand leaveChannelCommand)
    {
        ch.leaveChannel();
        ch.sendMessage(new LeaveChannelResponse(leaveChannelCommand.getChannelName()));
    }

    @Override
    public void handle(SendMessageInChannelCommand sendMessageInChannelCommand)
    {
        ch.getCurrentChannel().broadcast(sendMessageInChannelCommand.getMessage()); // send message to all users in the channel
        ch.sendMessage(new SendMessageInChannelResponse(sendMessageInChannelCommand.getMessage()));
    }

    @Override
    public void handle(JoinChannelCommand joinChannelCommand) {
        ch.joinChannel(joinChannelCommand.getChannelName());
        ch.sendMessage(new JoinChannelResponse(joinChannelCommand.getChannelName()));
    }


    @Override
    public void handle(CreateChannelCommand createChannelCommand) {
        // ch.createChannel(createChannelCommand.getChannelName());
    }

    @Override
    public void handle(MessageInChannel messageInChannel)
    {

    }
}
