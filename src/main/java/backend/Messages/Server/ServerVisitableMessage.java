package backend.Messages.Server;

/**
 * Interface for a server message that can be visited by a {@code ServerMessageVisitor}.
 */
public interface ServerVisitableMessage {
    void accept(ServerMessageVisitor serverMessageVisitor);
}
