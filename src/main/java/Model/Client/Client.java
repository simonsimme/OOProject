package Model.Client;

import Model.Messages.UI.UIMessage;
import Model.Messages.UI.UpdateChannels;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade for the client Provides methods for managing channels, send messages,
 * and notifying observers of updates. Acts as a central interface for client-side operations.
 */
public class Client implements ClientSubject{
    private String userName;
    private final ClientChannelRecord channelRecord;
    private final ClientCommunicationManager communicationManager;
    private final List<ClientObserver> observers = new ArrayList<>();

    //Client will hold a history of all channels it has been in. Less required for the client, but better for the server.
    //private final Map<String, StringBuilder> history = new HashMap<String, StringBuilder>();

    /**
     * Client's only constructor, requires the address to connect to and a port.
     * @param address The address that the Socket connects to. (Has to be "localhost" for now)
     * @param port The port that the Socket connects to. (Has to match with server port)
     */
    public Client(String address, int port) {
        this.userName = "client default user name";
        this.channelRecord = new ClientChannelRecord();
        communicationManager = new ClientCommunicationManager(address,port,this.channelRecord,observers);
        new Thread(communicationManager).start();
    }
    /**
     * Gets the username of the client.
     * @return the client's username.
     */
    public String getUserName()
    {
        return userName;
    }
    /**
     * Sets a nickname for the client.
     */
    public String getCurrentChannelName(){
        return channelRecord.getCurrentChannelName();
    }
    public void setNickName(String name)
    {
        userName = name;
    }
    /**
     * Sets the username for the client.
     * @param userName the new username for the client.
     */
    public void setUserName(String userName){
        userName = userName;
    }
    /**
     * Creates a new channel with the given name and password.
     * @param channelName the name of the channel to create.
     * @param password    the password for the channel.
     */
    public void createChannel(String channelName, String password)
    {
        communicationManager.createChannel(userName,channelName,password);
    }
    /**
     * Joins an existing channel with the given name and password.
     * @param channelName the name of the channel to join.
     * @param password    the password for the channel.
     */
    public void joinChannel(String channelName, String password){
        try {
            communicationManager.joinChannel(userName, channelName, password);
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    /**
     * Leaves the current channel the client is in.
     */
    public void leaveChannel(){
        communicationManager.leaveChannel(userName, channelRecord.getCurrentChannelName());
    }
    public void leaveChannel(String channelName){
        communicationManager.leaveChannel(userName,channelName);
    }
    /**
     * Switches to a specified channel by its name.
     * @param channelName the name of the channel to switch to.
     */
    public void switchChannel(String channelName){
        channelRecord.switchToChannel(channelName);
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(),channelRecord.getCurrentChannelName()));
        communicationManager.getChannelHistory(userName,channelName);
    }

    /**
     * Switches the user's current channel to the next available channel in the list.
     */
    public void switchChannel(){
        String newChannelName = channelRecord.switchToNextChannel();
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(),channelRecord.getCurrentChannelName()));
        communicationManager.getChannelHistory(userName,newChannelName);
    }
    /**
     * Switches to the next available channel in the channel record.
     */
    //TODO NO uses at all?
    public void nextChannel(){
        channelRecord.switchToNextChannel();
        notifyObservers(new UpdateChannels(channelRecord.getChannelNames(),channelRecord.getCurrentChannelName()));
    }
    //TODO Is this not used?
    public StringBuilder getHistory(){
        return channelRecord.getCurrentChannelHistory();
    }

    /**
     * Sends a message to the current channel.
     * @param message the message to send.
     */
    public void sendMessage(String message)
    {
        communicationManager.sendMessage(userName,channelRecord.getCurrentChannelName(), message, false);
    }
    /**
     * Retrieves a list of names of all channels the client is a member of.
     *
     * @return a list of channel names.
     */
    public List<String> getChannelNames(){
        return channelRecord.getChannelNames();
    }
    /**
     * Retrieves a list of usernames in the current channel.
     *
     * @return a list of usernames in the current channel.
     */
    public List<String> getUserNamesInCurrentChannel(){
        return channelRecord.getUsersInCurrentChannel();
    }

    /**
     * Disconnect the user from channel and server
     */
    public void disconnect() {
        communicationManager.disconnect(userName, channelRecord.getCurrentChannelName());
    }
    /**
     * Attaches an observer to the client for receiving updates.
     *
     * @param observer the observer to attach.
     */
    public void attach( ClientObserver observer){
        observers.add(observer);
    }
    /**
     * Detaches an observer from the client to stop receiving updates.
     *
     * @param observer the observer to detach.
     */
    public void detach(ClientObserver observer){
         observers.add(observer);
    }
    /**
     * Notifies all attached observers with a given message.
     *
     * @param message the message to notify observers with.
     */
    @Override
    public void notifyObservers(UIMessage message) {
        for (ClientObserver observer: observers) {
            observer.update(message);
        }
    }
}