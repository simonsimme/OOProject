package backend.client_model;

import backend.Messages.UI.UIMessage;
import backend.Messages.UI.UpdateChannels;
import java.util.ArrayList;
import java.util.List;


/**
 * Facade for the client Provides methods for managing channels, send messages,
 * and notifying observers of updates. Acts as a central interface for client-side operations.
 */
public class Client implements ClientSubject{
    private String user;
    private final ClientChannelGroup channelGroup;
    private final ClientCommunicationManager cm;
    private final List<ClientObserver> observers = new ArrayList<>();

    /**
     * Client's only constructor, requires the address to connect to and a port.
     * @param address The address that the Socket connects to. (Has to be "localhost" for now)
     * @param port The port that the Socket connects to. (Has to match with server port)
     */
    public Client(String address, int port) {
        this.user = "client default user name";
        this.channelGroup = new ClientChannelGroup();
        cm = new ClientCommunicationManager(address,port,this.channelGroup,observers);
        new Thread(cm).start();

    }
    /**
     * Gets the username of the client.
     * @return the client's username.
     */
    public String getUserName()
    {
        return user;
    }
    /**
     * Sets a nickname for the client.
     * @param name the new nickname for the client.
     */
    public void setNickName(String name)
    {
        user = name;
    }
    /**
     * Sets the username for the client.
     * @param userName the new username for the client.
     */
    public void setUserName(String userName){
        user = userName;
    }
    /**
     * Creates a new channel with the given name and password.
     * @param channelName the name of the channel to create.
     * @param password    the password for the channel.
     */
    public void createChannel(String channelName, String password)
    {
        cm.createChannel(user,channelName,password);
    }
    /**
     * Joins an existing channel with the given name and password.
     * @param channelName the name of the channel to join.
     * @param password    the password for the channel.
     */
    public void joinChannel(String channelName, String password){
        cm.joinChannel(user, channelName, password);
    }
    /**
     * Leaves the current channel the client is in.
     */
    public void leaveChannel(){
        cm.leaveChannel(user,channelGroup.getCurrentChannel().getChannelName());
    }
    /**
     * Switches to a specified channel by its name.
     * @param channelName the name of the channel to switch to.
     */
    public void switchChannel(String channelName){
        channelGroup.switchToChannel(channelName);
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }
    /**
     * Switches to the next available channel in the channel group.
     */
    public void nextChannel(){
        channelGroup.switchToNextChannel();
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));

    }
    /**
     * Sends a message to the current channel.
     * @param message the message to send.
     */
    public void sendMessage(String message)
    {
        System.out.println("Curr channel: " + channelGroup.getCurrentChannel().getChannelName());
        cm.sendMessage(user,channelGroup.getCurrentChannel().getChannelName(), message);
    }
    /**
     * Retrieves a list of names of all channels the client is a member of.
     *
     * @return a list of channel names.
     */
    public List<String> getChannelNames(){
        return channelGroup.getChannelNames();
    }
    /**
     * Retrieves a list of usernames in the current channel.
     *
     * @return a list of usernames in the current channel.
     */
    public List<String> getUserNamesInCurrentChannel(){
        return channelGroup.getCurrentChannel().getUsersInChannel();
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