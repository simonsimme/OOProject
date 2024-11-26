package backend.Messages.Client;

import backend.Messages.Client.ClientMessageVisitor;

public interface ClientVisitableMessage {
    void accept(ClientMessageVisitor visitor);
}
