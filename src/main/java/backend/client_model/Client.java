package backend.client_model;

import backend.Messages.UI.UIMessage;
import backend.Messages.UI.UpdateChannels;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade for the client
 */
public class Client implements ClientSubject{

    private String user;

    private ClientChannelGroup channelGroup;

    private ClientCommunicationManager cm;

    private List<ClientObserver> observers = new ArrayList<>();

    /**
     * Client's only constructor, requires the adress to connect to and a port.
     * @param adress The adress that the Socket connects to. (Has to be "localhost" for now)
     * @param port The port that the Socket connects to. (Has to match with server port)
     */
    public Client(String adress, int port) {
        this.user = "client default user name";
        this.channelGroup = new ClientChannelGroup();
        cm = new ClientCommunicationManager(adress,port,this.channelGroup,observers);
        new Thread(cm).start();

    }

    public String getUserName()
    {
        return user;
    }
    public void setNickName(String name)
    {
        user = name;
    }
    public void setUserName(String userName){
        user = userName;
    }

    public void createChannel(String channelName, String password)
    {
        cm.createChannel(user,channelName,password);
    }

    public void joinChannel(String password){
        cm.joinChannel(user,channelGroup.getCurrentChannel().getChannelName(),password);
    }
    public void leaveChannel(){
        cm.leaveChannel(user,channelGroup.getCurrentChannel().getChannelName());
    }

    public void switchChannel(String channelName){
        channelGroup.switchToChannel(channelName);
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }
    public void nextChannel(){
        channelGroup.switchToNextChannel();
        notifyObservers(new UpdateChannels(channelGroup.getChannelNames(),channelGroup.getCurrentChannel().getChannelName()));
    }

    public void sendMessage(String message)
    {
        cm.sendMessage(user,channelGroup.getCurrentChannel().getChannelName(),message);
    }

    public List<String> getChannelNames(){
        return channelGroup.getChannelNames();
    }

    public List<String> getUserNamesInCurrentChannel(){
        return channelGroup.getCurrentChannel().getUsersInChannel();
    }


    public void attach( ClientObserver observer){
        observers.add(observer);
    }

    public void detach(ClientObserver observer){
         observers.add(observer);
    }

    @Override
    public void notifyObservers(UIMessage message) {
        for (ClientObserver observer: observers) {
            observer.update(message);
        }
    }


}