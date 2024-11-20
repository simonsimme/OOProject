package backend.Messages;

public interface Visitor
{
    void handle(LeaveChannelCommand leaveChannelCommand);
    void handle(SendMessageInChannelCommand sendMessageInChannelCommand);
    void handle(JoinChannelCommand joinChannelCommand);
    void handle(CreateChannelCommand createChannelCommand);

    void handle(MessageInChannel messageInChannel);


}
