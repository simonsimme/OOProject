package Model.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        currentChannel.setName(name);
    }

    /**
     * Manipulates currentChannel.
     * @param channelName The name of the channel to switch to.
     */
    public void switchToChannel(String channelName){

        for (ClientChannel channel : channels) {
            System.out.println(channel.getName());
            if(channel.getName().equals(channelName) ){
                currentChannel = channel;
                break;
            }
        }
    }

    public String switchToNextChannel(){
        int i = channels.indexOf(currentChannel);
        i = (i+1) % channels.size();
        switchToChannel(channels.get(i).getName());
        return channels.get(i).getName();
    }

    /**
     * Saves a record of the given message in the given channel
     * @param message
     * @param channelName
     */
    public void recordMessageInChannel(String message, String channelName){
        for (ClientChannel channel: channels) {
            if(channel.getName() == channelName){
                channel.recordMessage(message);
                break;
            }
        }
    }

    public void addNewChannel(String channelName){
        System.out.println("Adding new channel: " + channelName);
        channels.add(new ClientChannel(channelName));
        currentChannel = channels.get(channels.size()-1);
    }

    /**
     * Removes the channel with the given name from the list of channels.
     * @param channelName The name of the channel to remove.
     *
     * If the channel to be removed is the current channel, the next channel in the list is switched to.
     * If there are no more channels left, the current channel is set to an empty channel.
     *
     * */
    public void removeChannel(String channelName)
    {
        for (ClientChannel channel:  channels)
        {
             if(Objects.equals(channel.getName(), channelName))
             {
                  channels.remove(channel);

                 if (!channels.isEmpty()) // If there are still channels left
                 {
                     switchToNextChannel();
                 }
                 else
                 {
                     currentChannel = new ClientChannel("empty-channel");
                 }
                 break;
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

    public String getCurrentChannelName(){
        return currentChannel.getName();
    }

    public StringBuilder getCurrentChannelHistory(){
        return currentChannel.getHistory();
    }

    public List<String> getUsersInCurrentChannel(){
        return currentChannel.getUsers();
    }

    public List<String> getChannelNames(){
        List<String> result = new ArrayList<>();
        for (ClientChannel channel:channels) {
             result.add(channel.getName());
        }
        return result;
    }


}
