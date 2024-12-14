package Model.Messages.Client;

/**
 * A visitable message that accepts a ClientMessageVisitor
 * All Client message classes implement this interface, see Visitor pattern.
 */
public interface ClientVisitableMessage {
    void accept(ClientMessageVisitor visitor);
}
