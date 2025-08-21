package Model.Messages.Client;

import Model.Messages.Message;

/**
 * All messages that the ClientVisitor can handle should extend this.
 */
public interface ClientMessage extends Message {
    void accept(ClientMessageVisitor clientMessageVisitor);
}

