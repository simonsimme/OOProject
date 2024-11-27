package backend.client_model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a client-side model of a communication channel.
 * Tracks the channel's name, users, and message history.
 */
public class ClientChannel {
    private String channelName;
    private List<String> usersInChannel;
    private StringBuilder history;

    /**
     * Constructs a new {@code ClientChannel} with the specified name.
     * Initializes an empty list of users and an empty message history.
     *
     * @param channelName the name of the channel.
     */
    public ClientChannel(String channelName) {
        this.channelName = channelName;
        this.usersInChannel = new ArrayList<>();
        this.history = new StringBuilder();
    }

    /**
     * Constructs a copy of an existing {@code ClientChannel}.
     *
     * @param clientChannel the {@code ClientChannel} to copy.
     */
    public ClientChannel(ClientChannel clientChannel){
        this.channelName = clientChannel.getChannelName();
        this.usersInChannel = new ArrayList<>(clientChannel.getUsersInChannel());
        this.history = new StringBuilder(clientChannel.getHistory());
    }

    /**
     * Constructs a {@code ClientChannel} with the specified parameters.
     *
     * @param channelName    the name of the channel.
     * @param usersInChannel an array of usernames in the channel.
     * @param history        the history of messages in the channel.
     */
    public ClientChannel(String channelName, String[] usersInChannel, StringBuilder history){
        this.channelName = channelName;
        this.usersInChannel = Arrays.stream(usersInChannel).toList();
        this.history = history;
    }
    /**
     * Appends a message to the channel's history.
     *
     * @param message the message to add to the history.
     */
    public void sendMessage(String message){
        history.append(message);
    }
    /**
     * Sets the list of users currently in the channel.
     *
     * @param usersInChannel a list of usernames to set.
     */
    public void setUsersInChannel(List<String> usersInChannel){
        this.usersInChannel = usersInChannel;
    }
    /**
     * Retrieves the list of users currently in the channel.
     *
     * @return a list of usernames in the channel.
     */
    public List<String> getUsersInChannel(){
        return new ArrayList<>(usersInChannel);
    }
    /**
     * Adds a user to the channel.
     *
     * @param userName the name of the user to add.
     */
    public void addUserToChannel(String userName){
        usersInChannel.add(userName);
    }
    /**
     * Retrieves the name of the channel.
     *
     * @return the name of the channel.
     */
    public String getChannelName(){
        return channelName;
    }
    /**
     * Sets the message history for the channel.
     * This method is private as it is not intended for external use.
     *
     * @param history the message history to set.
     */
    private void setHistory(String history){
        this.history = new StringBuilder(history);
    }
    /**
     * Retrieves the channel's message history as a string.
     *
     * @return the channel's message history.
     */
    public String getHistory(){
        return history.toString();
    }
    /**
     * Saves the channel's history to persistent storage.
     * <p>
     * This method is currently a placeholder and needs implementation.
     */
    public void saveHistory(){
        /*
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(channelName + "_history.txt",false))) {
            writer.write(history.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }
    /**
     * Loads the channel's history from persistent storage.
     * <p>
     * This method is currently a placeholder and needs implementation.
     */
    private void loadHistory(){
        //TODO : Implement saving feature
    }
    public void setChannelName(String name){
        channelName = name;
    }

}
