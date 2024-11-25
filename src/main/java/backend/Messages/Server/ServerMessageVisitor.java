package backend.Messages.Server;

import backend.Messages.Client.MessageInChannel;

public interface ServerMessageVisitor
{
    void handle(LeaveChannelCommand leaveChannelCommand);
    void handle(SendMessageInChannelCommand sendMessageInChannelCommand);
    void handle(JoinChannelCommand joinChannelCommand);
    void handle(CreateChannelCommand createChannelCommand);

}
