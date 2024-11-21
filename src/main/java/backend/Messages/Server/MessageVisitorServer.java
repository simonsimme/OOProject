package backend.Messages.Server;

import backend.ClientHandler;
import backend.Messages.Client.JoinChannelResponse;
import backend.Messages.Client.LeaveChannelResponse;
import backend.Messages.Client.MessageInChannel;
import backend.Messages.Client.SendMessageInChannelResponseClient;

public class MessageVisitorServer implements ServerMessageVisitor {
    private final ClientHandler clientHandler;

    public MessageVisitorServer(ClientHandler clientHandler)
    {
        this.clientHandler = clientHandler;
    }

    @Override
    public void handle(LeaveChannelCommand leaveChannelCommand)
    {
        clientHandler.leaveChannel();
        clientHandler.sendMessage(new LeaveChannelResponse(leaveChannelCommand.getChannelName()));
    }

    @Override
    public void handle(SendMessageInChannelCommand message)
    {
        clientHandler.getCurrentChannel().broadcast(message, clientHandler); // send message to all users in the channel
        clientHandler.sendMessage(new SendMessageInChannelResponseClient(message.getUserName(), message.getChannelName(), message.getMessage()));
    }

    @Override
    public void handle(JoinChannelCommand joinChannelCommand) {
        clientHandler.joinChannel(joinChannelCommand.getChannelName());
        clientHandler.sendMessage(new JoinChannelResponse(joinChannelCommand.getChannelName()));
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
