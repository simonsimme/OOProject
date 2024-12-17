package Model.Messages.Server;

/**
 * Interface for a visitor that handles various server messages.
 * <p>
 * This interface defines methods to process specific server commands,
 * following the Visitor design pattern.
 */
public interface ServerMessageVisitor {

    /**
     * Handles a {@code LeaveChannelCommand}.
     *
     * @param leaveChannelCommand the command to leave a channel
     */
    void handle(LeaveChannelCommand leaveChannelCommand);

    /**
     * Handles a {@code SendMessageInChannelCommand}.
     *
     * @param sendMessageInChannelCommand the command to send a message in a channel
     */
    void handle(SendMessageInChannelCommand sendMessageInChannelCommand);

    /**
     * Handles a {@code JoinChannelCommand}.
     *
     * @param joinChannelCommand the command to join a channel
     */
    void handle(JoinChannelCommand joinChannelCommand);

    /**
     * Handles a {@code CreateChannelCommand}.
     *
     * @param createChannelCommand the command to create a channel
     */
    void handle(CreateChannelCommand createChannelCommand);

    /**
     * Handles a {@code RetrieveChatHistoryCommand}.
     *
     * @param retrieveChatHistoryCommand the command to retrieve chat history for a channel
     */
    void handle(RetrieveChatHistoryCommand retrieveChatHistoryCommand);
}
