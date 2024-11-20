package backend.Messages;

public interface VisitableMessage {
    void accept(VisitorMessage visitor);
}
