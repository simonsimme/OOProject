package Model.Messages.Client;

import Model.Messages.Message;

/**
 * All messages that the ClientVisitor can handle should extend this.
 */
public abstract class ClientMessage extends Message implements ClientVisitableMessage {}
