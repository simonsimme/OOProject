package Model.Messages.Client;

/**
 * Represents a response sent to a client, containing the chat history for a specific channel.
 * This class extends {@code ClientMessage} and includes the channel name and its associated history.
 */
public class RetrieveChatHistoryResponse implements ClientMessage, ClientVisitableMessage {
    /**
     * The name of the channel for which the chat history is being retrieved.
     */
    private final String channelName;

    /**
     * The chat history of the specified channel, represented as a {@code StringBuilder}.
     */
    private final StringBuilder history;

    /**
     * Constructs a {@code RetrieveChatHistoryResponse} with the specified channel name and chat history.
     *
     * @param channelName The name of the channel for which the chat history is retrieved.
     * @param history     The chat history of the channel, represented as a {@code StringBuilder}.
     */
    public RetrieveChatHistoryResponse(String channelName, StringBuilder history){
        this.channelName = channelName;
        this.history = history;
    }

    /**
     * Gets the name of the channel for which the chat history is being retrieved.
     *
     * @return The channel name.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Returns a string representation of the {@code RetrieveChatHistoryResponse}, including the channel name and chat history.
     *
     * @return A string representation of the response.
     */
    @Override
    public String toString() {
        return "RetrieveChatHistoryResponse{" +
                "channelName='" + channelName + '\'' +
                ", history=" + history +
                '}';
    }

    /**
     * Accepts a visitor to handle this {@code RetrieveChatHistoryResponse} as part of the Visitor pattern.
     *
     * @param visitor The visitor that processes this response.
     */
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
