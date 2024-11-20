package backend.Messages;

public interface VisitorMessage
{
    void handle(JoinChannelResponse joinChannelResponse);
    void handle(CreateChannelResponse createChannelResponse);
    void handle(LeaveChannelResponse leaveChannelResponse);
    void handle(SendMessageInChannelResponse sendMessageInChannelResponse);
}
