package backend.Messages.Server;



public interface ServerMessageVisitor
{
    void handle(LeaveChannelCommand leaveChannelCommand);
    void handle(SendMessageInChannelCommand sendMessageInChannelCommand);
    void handle(JoinChannelCommand joinChannelCommand);
    void handle(CreateChannelCommand createChannelCommand);
}
