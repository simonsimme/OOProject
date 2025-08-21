package Model.Messages.Client;

/**
 * An interface for the visitor that handles the different types of messages sent to the client.
 * ClientVisitor implements this interface, see Visitor pattern.
 */
public interface ClientMessageVisitor
{
    void handle(JoinChannelResponse m);
    void handle(CreateChannelResponse m);
    void handle(LeaveChannelResponse m);
    void handle(ErrorResponse m);
    void handle(MessageInChannel m);
    void handle(RetrieveChatHistoryResponse m);
}
