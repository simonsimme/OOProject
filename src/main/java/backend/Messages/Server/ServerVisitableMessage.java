package backend.Messages.Server;

public interface ServerVisitableMessage {
    void accept(ServerMessageVisitor serverMessageVisitor);
}
