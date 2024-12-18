package Model.Server.saving;

import Model.Messages.Message;
import Model.Server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

public class ChattChannelHelper {
    private String name; // Name of the channel
    private List<ClientHandler> members; // List of users in the channel
    private List<Message> messages; // List of messages in the channel

    // Constructor
    public ChattChannelHelper(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Add a user to the channel
    public void addMember(ClientHandler user) {
        if (!members.contains(user)) {
            members.add(user);
        }
    }

    // Remove a user from the channel
    public void removeMember(ClientHandler user) {
        members.remove(user);
    }

    // Get all members
    public List<ClientHandler> getMembers() {
        return members;
    }

    // Add a message to the channel
    public void addMessage(Message message) {
        messages.add(message);
    }

    // Get all messages
    public List<Message> getMessages() {
        return messages;
    }
}
