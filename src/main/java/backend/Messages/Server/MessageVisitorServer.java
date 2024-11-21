package backend.Messages.Server;

import backend.ClientHandler;
import backend.Server;
import backend.Messages.Client.JoinChannelResponse;
import backend.Messages.Client.LeaveChannelResponse;
import backend.Messages.Client.MessageInChannel;
import backend.Messages.Client.SendMessageInChannelResponseClient;

public class MessageVisitorServer implements ServerMessageVisitor {
    private ClientHandler clientHandler;

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
    public void handle(SendMessageInChannelCommand sendMessageInChannelCommand)
    {
        clientHandler.getCurrentChannel().broadcast(sendMessageInChannelCommand.getMessage()); // send message to all users in the channel
        clientHandler.sendMessage(new SendMessageInChannelResponseClient(sendMessageInChannelCommand.getMessage()));
    }

    @Override
    public void handle(JoinChannelCommand joinChannelCommand) {

        if(clientHandler.getCurrentChannel() != null) {
            clientHandler.getCurrentChannel().removeClient(clientHandler);
        }
        Server server = clientHandler.getServer();
        clientHandler.setCurrentChannel(server.getOrCreateChannel(joinChannelCommand.getChannelName()));
        clientHandler.getCurrentChannel().removeClient(clientHandler);
        MessageInChannel msg = new MessageInChannel("Server", joinChannelCommand.getChannelName(), "Joined channel: " + joinChannelCommand.getChannelName());
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
