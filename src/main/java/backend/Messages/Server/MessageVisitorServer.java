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
    /**
     * Handles the SendMessageInChannelCommand, sending a message to the current chat channel if the user is in one.
     *
     * @param message the {@link SendMessageInChannelCommand} containing the details of the message, including
     *                the sender's username, the channel name, and the message content.
     */
    @Override
    public void handle(SendMessageInChannelCommand message) {
        ChatChannel currentChannel = clientHandler.getCurrentChannel();
        if (currentChannel == null) {
            clientHandler.sendMessage(new ErrorResponse("You are not in a channel"));
        } else {
            currentChannel.broadcast(new MessageInChannel(message.getUserName(), message.getChannelName(), message.getMessage()));
        }
    }
    @Override
    public void handle(JoinChannelCommand joinChannelCommand) {
        try {
            clientHandler.joinChannel(joinChannelCommand.getChannelName(), joinChannelCommand.getPassword());
            clientHandler.sendMessage(new JoinChannelResponse(joinChannelCommand.getChannelName()));

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
      }

    @Override
    public void handle(CreateChannelCommand createChannelCommand) {
        clientHandler.createChannel(createChannelCommand.getChannelName(), createChannelCommand.getChannelPassword());
        clientHandler.sendMessage(new CreateChannelResponse(createChannelCommand.getChannelName()));
    }
}
