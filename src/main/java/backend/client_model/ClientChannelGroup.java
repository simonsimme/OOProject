package backend.client_model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientChannelGroup {
    private List<ClientChannel> channels;
    private ClientChannel currentChannel;

    public ClientChannelGroup(){
        this.channels = new ArrayList<>();
        this.currentChannel = new ClientChannel("empty-channel");
    }

    public void switchToChannel(String channelName){
        for (ClientChannel channel : channels) {
            if(channel.getChannelName() == channelName){
                currentChannel = channel;
                break;
            }
        }
    }

    public void switchToNextChannel(){
        int i = channels.indexOf(currentChannel);
        i = i % channels.size();
        currentChannel = channels.get(i);
    }

    public void sendMessageInChannel(String message, String channelName){
        for (ClientChannel channel: channels) {
            if( channel.getChannelName() == channelName){
                channel.sendMessage(message);
                break;
            }
        }
    }

    public void addNewChannel(String channelName){
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
