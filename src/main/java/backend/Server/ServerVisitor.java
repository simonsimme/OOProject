package backend.Server;

import backend.Messages.Client.*;
import backend.Messages.Server.*;

/**
 * The {@code ServerVisitor} class implements the {@link ServerMessageVisitor} interface
 * and is responsible for processing commands from the clients. It interacts with the
 * {@link ClientHandler} to execute actions such as joining, leaving, creating channels or sending messages.
 *
 * This class uses the Visitor pattern to process different types of commands encapsulated in message objects.
 * It handles errors converting them into {@code ErrorResponse} messages for the client.
 */
public class ServerVisitor implements ServerMessageVisitor {
    private final ClientHandler clientHandler;

    /**
     * Construct a {@code ServerVisitor} for a specific {@link ClientHandler}
     *
     * @param clientHandler the {@code ClientHandler} instance that represents the client
     * sending commands.
     */
    public ServerVisitor(ClientHandler clientHandler)
    {
        this.clientHandler = clientHandler;
    }

    /**
     * Process a {@link LeaveChannelCommand}, removing the client from the current channel.
     * If successful, a {@link LeaveChannelResponse} is sent to the client. If an error an
     * {@link ErrorResponse} is sent.
     * @param leaveChannelCommand the command to leave the current channel.
     */
    @Override
    public void handle(LeaveChannelCommand leaveChannelCommand)
    {
        try {
            clientHandler.leaveChannel();
            clientHandler.sendMessage(new LeaveChannelResponse(leaveChannelCommand.getChannelName()));
        } catch (OperationException e)
        {
            handleOperationException(e);
        }
    }
    /**
     * Process a {@link SendMessageInChannelCommand}, sending the message to the current chat channel.
     * If an error occurs an {@link ErrorResponse} is sent.
     * @param message the command containing details about the sender.
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

    /**
     * Process a {@link JoinChannelCommand}, adding the client to the specific channel.
     * If successful, a {@link JoinChannelResponse} is sent to the client. If an error
     * occurs an {@link ErrorResponse} is sent
     * @param joinChannelCommand the command containing information to join a channel.
     */
    @Override
    public void handle(JoinChannelCommand joinChannelCommand) {
        try {
            clientHandler.joinChannel(joinChannelCommand.getChannelName(), joinChannelCommand.getPassword());
            clientHandler.sendMessage(new JoinChannelResponse(joinChannelCommand.getChannelName()));

        } catch (OperationException e) {
            handleOperationException(e);
        }
      }

    /**
     * Process a {@link CreateChannelCommand}, creating a new chat channel. If successful the client
     * is automatically added to the new chat channel, and a {@link CreateChannelResponse} is sent.
     * If an error occurs (e.g. the channel already exist), an {@link ErrorResponse} is sent.
     * @param createChannelCommand
     */
    @Override
    public void handle(CreateChannelCommand createChannelCommand) {
        try {
            clientHandler.createChannel(createChannelCommand.getChannelName(), createChannelCommand.getChannelPassword());
            clientHandler.sendMessage(new CreateChannelResponse(createChannelCommand.getChannelName()));
        }
        catch (OperationException e) {
            handleOperationException(e);
        }
    }

    /**
     * Handles exceptions throw during command execution, converting them into an
     * {@link ErrorResponse} and sending it to the client.
     * @param e the {@code OperationException} containing the error message.
     */
    private void handleOperationException(OperationException e) {
        clientHandler.sendMessage(new ErrorResponse(e.getMessage()));
    }
}
