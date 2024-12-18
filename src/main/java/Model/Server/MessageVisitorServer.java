package Model.Server;

import Model.Messages.Client.*;
import Model.Messages.Server.*;

public class MessageVisitorServer implements ServerMessageVisitor {
    private final ClientHandler clientHandler;

    public MessageVisitorServer(ClientHandler clientHandler)
    {
        this.clientHandler = clientHandler;
    }

    /**
     * Handles the LeaveChannelCommand, removing the client from the specified channel if they are in it.
     * if clientHandler.leaveChannel returns true, the clientHandler sends a LeaveChannelResponse to the client.
     * @param leaveChannelCommand containing the name of the channel to leave.
     */
    @Override
    public void handle(LeaveChannelCommand leaveChannelCommand)
    {
        if(clientHandler.leaveChannel(leaveChannelCommand.getChannelName())){
            String channelName = leaveChannelCommand.getChannelName();
            clientHandler.sendMessage(new LeaveChannelResponse(channelName));
        }
    }
    /**
     * Handles the SendMessageInChannelCommand, sending a message to the current chat channel if the user is in one.
     * Reads destination channel from the message and sends the message to that channel via broadcast.
     * @param message the {@link SendMessageInChannelCommand} containing the details of the message, including
     *                the sender's username, the channel name, and the message content.
     */
    @Override
    public void handle(SendMessageInChannelCommand message) {
        ChatChannel channelToSendIn = clientHandler.getChannel(message.getChannelName());
        if (channelToSendIn == null) {
            clientHandler.sendMessage(new ErrorResponse("You are not in a channel"));
        } else {
            channelToSendIn.broadcast(new MessageInChannel(message.getUserName(), message.getChannelName(), message.getMessage(), message.isServerMessage()));
        }
    }
    /**
     * Handles JoinChannelCommand
     * If the clientHandler.joinChannel returns true, the clientHandler sends a JoinChannelResponse to the client.
     * @param joinChannelCommand containing the name of the channel to join.
     * */
    @Override
    public void handle(JoinChannelCommand joinChannelCommand) {
        try {

            if (clientHandler.joinChannel(joinChannelCommand.getChannelName(), joinChannelCommand.getPassword())) {
                clientHandler.sendMessage(new JoinChannelResponse(joinChannelCommand.getChannelName()));
                ChatChannel channel = clientHandler.getChannel(joinChannelCommand.getChannelName());
                clientHandler.sendMessage(new RetrieveChatHistoryResponse(channel.getName(), channel.getChatChannelHistory()));
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
      }

      /**
       * Handles CreateChannelCommand
       * If the clientHandler.createChannel returns true, the clientHandler sends a CreateChannelResponse to the client.
       * @param createChannelCommand containing the name of the channel to create.
       * */
    @Override
    public void handle(CreateChannelCommand createChannelCommand) {
        if(clientHandler.createChannel(createChannelCommand.getChannelName(), createChannelCommand.getChannelPassword())) {
            clientHandler.sendMessage(new CreateChannelResponse(createChannelCommand.getChannelName()));
        }
    }

    @Override
    public void handle(RetrieveChatHistoryCommand retrieveChatHistoryCommand) {
        ChatChannel channel = clientHandler.getChannel(retrieveChatHistoryCommand.getChannelName());
        if (channel == null) {
            clientHandler.sendMessage(new ErrorResponse("You are not in a channel"));
        } else {
            clientHandler.sendMessage(new RetrieveChatHistoryResponse(channel.getName(), channel.getChatChannelHistory()));
        }
    }


}
