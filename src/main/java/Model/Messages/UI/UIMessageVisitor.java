package Model.Messages.UI;

/**
 * Visitor interface for handling different types of UI messages.
 * This interface defines methods for handling specific message types that implement the {@link UIMessage} interface.
 */
public interface UIMessageVisitor {
    /**
     * Handles a {@link DisplayError} message.
     * This method is called when a {@code DisplayError} message is encountered.
     * @param e The {@code DisplayError} message to be handled.
     */
    void handle(DisplayError e);
    /**
     * Handles a {@link DisplayMessage} message.
     * This method is called when a {@code DisplayMessage} message is encountered.
     *
     * @param m The {@code DisplayMessage} to be handled.
     */
    void handle(DisplayMessage m);
    /**
     * Handles an {@link UpdateChannels} message.
     * This method is called when an {@code UpdateChannels} message is encountered.
     *
     * @param u The {@code UpdateChannels} message to be handled.
     */
    void handle(UpdateChannels u);
    /**
     * Handles a {@link UIChannelHistory} message.
     * This method is called when a {@code UIChannelHistory} message is encountered.
     *
     * @param historyMessage The {@code UIChannelHistory} message to be handled.
     */
    void handle(UIChannelHistory historyMessage);

    void handle(DisplayChannelMessage displayChannelMessage);
}
