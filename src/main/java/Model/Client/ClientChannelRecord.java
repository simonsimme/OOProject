package Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A record of the channels that a client is connected to.
 * Manages the list of channels, the current active channel, and provides functionality
 * to switch channels, record messages, and maintain channel-specific history.
 */
public class ClientChannelRecord {
    /**
     * All channels that the local client has a record of.
     */
    private final List<ClientChannel> channels;
    /**
     * Reference to the element of "channels" that is the currently open channel in the UI.
     */
    private ClientChannel currentChannel;

    /**
     * Creates a new {@code ClientChannelRecord} with no initial channels.
     * Initializes the {@code currentChannel} to an empty channel.
     */
    public ClientChannelRecord(){
        this.channels = new ArrayList<>();
        this.currentChannel = new ClientChannel("empty-channel");
    }

    /**
     * Switches the active channel to the specified channel by name.
     *
     * @param channelName the name of the channel to switch to.
     * If the channel name does not exist in the record, no action is taken.
     */
    public void switchToChannel(String channelName){
        for (ClientChannel channel : channels) {
            if(channel.getName().equals(channelName) ){
                currentChannel = channel;
                break;
            }
        }
    }

    /**
     * Switches the active channel to the next channel in the list.
     *
     * @return the name of the next channel that is now active.
     * If the list is circular, the first channel is returned after the last one.
     */
    public String switchToNextChannel(){
        int i = channels.indexOf(currentChannel);
        i = (i+1) % channels.size();
        switchToChannel(channels.get(i).getName());
        return channels.get(i).getName();
    }

    /**
     * Records a message in the specified channel by name.
     *
     * @param message     the message to record.
     * @param channelName the name of the channel where the message should be recorded.
     * If the channel does not exist, no action is taken.
     */
    public void recordMessageInChannel(String message, String channelName){
        for (ClientChannel channel: channels) {
            if(Objects.equals(channel.getName(), channelName)){
                channel.recordMessage(message);
                break;
            }
        }
    }

    /**
     * Adds a new channel to the list of channels and sets it as the active channel.
     *
     * @param channelName the name of the new channel to add.
     */
    public void addNewChannel(String channelName){
        channels.add(new ClientChannel(channelName));
        currentChannel = channels.getLast();
    }

    /**
     * Removes the channel with the given name from the list of channels.
     * @param channelName The name of the channel to remove.
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

    /**
     * Retrieves the name of the currently active channel.
     *
     * @return the name of the current channel.
     */
    public String getCurrentChannelName(){
        return currentChannel.getName();
    }

    /**
     * Retrieves the names of all channels currently in the record.
     *
     * @return a list of channel names.
     */
    public List<String> getChannelNames(){
        List<String> result = new ArrayList<>();
        for (ClientChannel channel:channels) {
             result.add(channel.getName());
        }
        return result;
    }

    /**
     * Sets the message history for the specified channel.
     *
     * @param channelName   the name of the channel for which the history is being set.
     * @param stringBuilder the {@code StringBuilder} containing the history to set.
     * If the channel does not exist, no action is taken.
     */
    public void setChannelHistory(String channelName, StringBuilder stringBuilder) {
        for (ClientChannel channel: channels) {
            if(Objects.equals(channel.getName(), channelName)){
                channel.setHistory(String.valueOf(stringBuilder));
                break;
            }
        }
    }
}
