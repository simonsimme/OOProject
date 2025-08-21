package Model.Messages.Server;

import Model.Messages.Message;

/**
 * interface for all server messages.
 */
public interface ServerMessage extends Message {
    void accept(ServerMessageVisitor handler);
}
