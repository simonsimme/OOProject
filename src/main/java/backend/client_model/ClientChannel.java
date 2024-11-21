package backend.client_model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientChannel {
    private String channelName;
    private List<String> usersInChannel;
    private StringBuilder history;

    public ClientChannel(String channelName) {
        this.channelName = channelName;
    }

    public ClientChannel(ClientChannel clientChannel){
        this.channelName = clientChannel.getChannelName();
        this.usersInChannel = new ArrayList<>(clientChannel.getUsersInChannel());
        this.history = new StringBuilder(clientChannel.getHistory());
    }

    public ClientChannel(String channelName, String[] usersInChannel, StringBuilder history){
        this.channelName = channelName;
        this.usersInChannel = Arrays.stream(usersInChannel).toList();
        this.history = history;
    }

    public void sendMessage(String message){
        history.append(message);
    }

    public void setUsersInChannel(List<String> usersInChannel){
        this.usersInChannel = usersInChannel;
    }

    public List<String> getUsersInChannel(){
        return new ArrayList<>(usersInChannel);
    }

    public void addUserToChannel(String userName){
        usersInChannel.add(userName);
    }

    public String getChannelName(){
        return channelName;
    }

    private void setHistory(String history){
        this.history = new StringBuilder(history);
    }

    public String getHistory(){
        return history.toString();
    }

    public void saveHistory(){
        //TODO : Implement saving feature
    }

    private void loadHistory(){
        //TODO : Implement saving feature
    }

}
