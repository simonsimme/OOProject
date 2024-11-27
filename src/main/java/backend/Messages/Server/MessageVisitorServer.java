package backend.Messages.Server;

import backend.Messages.Client.*;
import backend.Server.ChatChannel;
import backend.Server.ClientHandler;

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
    public void handle(SendMessageInChannelCommand message) {
        ChatChannel currentChannel = clientHandler.getCurrentChannel();
        if (currentChannel == null) {
            clientHandler.sendMessage(new ErrorResponse("You are not in a channel"));
            System.out.println("You are not in a channel");
        } else {
            clientHandler.getCurrentChannel().broadcast((new MessageInChannel(message.getUserName(), message.getChannelName(), message.getMessage())), clientHandler); // send message to all users in the channel

            //clientHandler.sendMessage(new MessageInChannel(message.getUserName(), message.getChannelName(), message.getMessage()));
        }
    }
    @Override
    public void handle(JoinChannelCommand joinChannelCommand) {
        clientHandler.joinChannel(joinChannelCommand.getChannelName(), joinChannelCommand.getPassword());
        clientHandler.sendMessage(new JoinChannelResponse(joinChannelCommand.getChannelName()));
    }

    @Override
    public void handle(CreateChannelCommand createChannelCommand) {
        clientHandler.createChannel(createChannelCommand.getChannelName(), createChannelCommand.getChannelPassword());
        clientHandler.sendMessage(new CreateChannelResponse(createChannelCommand.getChannelName()));
    }
}
