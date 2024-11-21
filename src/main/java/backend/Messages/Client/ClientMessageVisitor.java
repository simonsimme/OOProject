package backend.Messages.Client;

import backend.Messages.Client.*;

public interface ClientMessageVisitor
{
    void handle(JoinChannelResponse m);
    void handle(CreateChannelResponse m);
    void handle(LeaveChannelResponse m);
    void handle(SendMessageInChannelResponseClient m);
    void handle(ErrorResponse m);
    void handle(MessageInChannel m);
}
