package Model.Client;


/**
 * Represents a client-side model of a communication channel.
 * Tracks the channel's name, and message history.
 */
public class ClientChannel {
    private String channelName;
    private StringBuilder history;

    /**
     * Constructs a new {@code ClientChannel} with the specified name.
     * Initializes an empty message history.
     *
     * @param channelName the name of the channel.
     */
    public ClientChannel(String channelName) {
        this.channelName = channelName;
        this.history = new StringBuilder();
    }

    /**
     * Appends a message to the channel's history.
     *
     * @param message the message to add to the history.
     */
    public void recordMessage(String message){
        history.append(message);
    }

    /**
     * Retrieves the channel's message history as a string.
     *
     * @return the channel's message history.
     */
    public StringBuilder getHistory() {
        return history;
    }

    /**
     * Retrieves the name of the channel.
     *
     * @return the name of the channel.
     */
    public String getName(){
        return channelName;
    }
    /**
     * Sets the message history for the channel.
     * This method is private as it is not intended for external use.
     *
     * @param history the message history to set.
     */
    void setHistory(String history){
        this.history = new StringBuilder(history);
    }

    public void setName(String name){
        channelName = name;
    }
}
