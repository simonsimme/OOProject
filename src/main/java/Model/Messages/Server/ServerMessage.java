package Model.Messages.Server;

import Model.Messages.Message;

/**
 * Abstract class for all server messages.
 */
public interface ServerMessage extends Message {
    public void accept(ServerMessageVisitor handler);
}
