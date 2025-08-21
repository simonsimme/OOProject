package Model.Server;

import Model.Messages.Client.*;
import Model.Messages.Server.*;

/**
 * {@code MessageVisitorServer} implements the {@link ServerMessageVisitor} interface and handles
 * incoming messages from the client. It processes various commands such as joining or leaving channels,
 * sending messages within channels, creating new channels, and retrieving chat history.
 * This class acts as a visitor to the different message types, delegating the appropriate actions
 * to the {@link ClientHandler} instance.
 */
public class MessageVisitorServer implements ServerMessageVisitor {
    private final ClientHandler clientHandler;

    /**
     * Constructor for the {@code MessageVisitorServer}.
     * Initializes the {@code ClientHandler} that this visitor will operate on.
     *
     * @param clientHandler the client handler managing the client session
     */
    public MessageVisitorServer(ClientHandler clientHandler)
    {
        this.clientHandler = clientHandler;
    }

    /**
     * Handles the {@link LeaveChannelCommand}, removing the client from the specified channel if they are in it.
     * If successful, a {@link LeaveChannelResponse} is sent to the client.
     *
     * @param leaveChannelCommand the command containing the name of the channel to leave
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
     * Handles the {@link SendMessageInChannelCommand}, broadcasting the message to the specified channel.
     * If the client is not in the channel, an error response is sent to the client.
     *
     * @param message the {@link SendMessageInChannelCommand} containing the details of the message
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
     * Handles the {@link JoinChannelCommand}, adding the client to the specified channel if possible.
     * If the client successfully joins the channel, a {@link JoinChannelResponse} is sent to the client.
     * Additionally, the client's chat history for that channel is retrieved and sent.
     *
     * @param joinChannelCommand the command containing the name of the channel to join
     */
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
     * Handles the {@link CreateChannelCommand}, creating a new channel with the specified name and password.
     * If the channel is successfully created, a {@link CreateChannelResponse} is sent to the client.
     *
     * @param createChannelCommand the command containing the name and password for the new channel
     */
    @Override
    public void handle(CreateChannelCommand createChannelCommand) {
        if(clientHandler.createChannel(createChannelCommand.getChannelName(), createChannelCommand.getChannelPassword())) {
            clientHandler.sendMessage(new CreateChannelResponse(createChannelCommand.getChannelName()));
        }
    }

    /**
     * Handles the {@link RetrieveChatHistoryCommand}, retrieving the chat history for the specified channel.
     * If the client is not in the channel, an error response is sent. Otherwise, the chat history is sent to the client.
     *
     * @param retrieveChatHistoryCommand the command containing the name of the channel to retrieve history for
     */
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
