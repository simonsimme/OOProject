package Model.Messages.Server;


/**
 * Interface for a visitor that handles server messages.
 */
public interface ServerMessageVisitor
{
    void handle(LeaveChannelCommand leaveChannelCommand);
    void handle(SendMessageInChannelCommand sendMessageInChannelCommand);
    void handle(JoinChannelCommand joinChannelCommand);
    void handle(CreateChannelCommand createChannelCommand);

    void handle(RetrieveChatHistoryRequest retrieveChatHistoryRequest);
}
