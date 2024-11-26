package backend.Messages.UI;

public interface UIVisitableMessage {
    void accept(UIMessageVisitor visitor);
}
