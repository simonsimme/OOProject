package backend.client_model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A record of the channels that a client is connected to.
 */
public class ClientChannelRecord {
    /**
     * All channels that the local client has a record of.
     */
    private List<ClientChannel> channels;
    /**
     * Reference to the element of "channels" that is the currently open channel in the UI.
     */
    private ClientChannel currentChannel;

    /**
     * Constructor
     */
    public ClientChannelRecord(){
        this.channels = new ArrayList<>();
        this.currentChannel = new ClientChannel("empty-channel");
    }
    public void setNameOfCurrentChannel(String name){
        currentChannel.setChannelName(name);
    }

    /**
     * Manipulates currentChannel.
     * @param channelName The name of the channel to switch to.
     */
    public void switchToChannel(String channelName){

        for (ClientChannel channel : channels) {
            System.out.println(channel.getChannelName());
            if(channel.getChannelName().equals(channelName) ){
                currentChannel = channel;
                break;
            }
        }
    }

    public ClientChannel switchToNextChannel(){
        int i = channels.indexOf(currentChannel);
        i = i % channels.size();
        currentChannel = channels.get(i);
        return currentChannel;
    }

    /**
     * Saves a record of the given message in the given channel
     * @param message
     * @param channelName
     */
    public void recordMessageInChannel(String message, String channelName){
        for (ClientChannel channel: channels) {
            if( channel.getChannelName() == channelName){
                channel.sendMessage(message);
                break;
            }
        }
    }

    public void addNewChannel(String channelName){
        System.out.println("Adding new channel: " + channelName);
        channels.add(new ClientChannel(channelName));
    }

    public void removeChannel(String channelName){
        for (ClientChannel channel:  channels) {
             if( channel.getChannelName() == channelName){
                  channels.remove(channel);
             }
        }

    }

    public void loadChannels(){
        //TODO : Implement saving feature

    }

    public void saveChannels(){
        //TODO : Implement saving feature
        for (ClientChannel channel: channels) {
            channel.saveHistory();
        }
    }

    public void updateChannels( ClientChannel[] channels){
        this.channels = Arrays.stream(channels).toList();
    }

    public void updateChannels(List<ClientChannel> channels ){
        this.channels = channels;
    }

    public ClientChannel getCurrentChannel(){
        return new ClientChannel(currentChannel);
    }

    public List<String> getUsersInCurrentChannel(){
        return currentChannel.getUsersInChannel();
    }

    public List<String> getChannelNames(){
        List<String> result = new ArrayList<>();
        for (ClientChannel channel:channels) {
             result.add(channel.getChannelName());
        }
        return result;
    }


}