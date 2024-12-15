package Model.Messages.UI;

/**
 * Interface for visitable UI messages.
 * This interface should be implemented by all UI messages that can accept a {@link UIMessageVisitor}.
 * The {@code accept} method allows the visitor to process the message.
 */
public interface UIVisitableMessage {

    /**
     * Accepts a visitor to handle the message.
     * This method allows the {@link UIMessageVisitor} to process the message it is called on.
     *
     * @param visitor The visitor that will handle the message.
     */
    void accept(UIMessageVisitor visitor);
}
