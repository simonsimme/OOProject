package Model.Server;

import Model.Messages.Message;
import Model.Server.saving.ChatSaver;
import Model.Server.saving.ChatSaverObserver;
import Model.Server.saving.SaveObserver;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/**
 * This class represents a chat channel in the server.
 * Each chat channel has a name and a password.
 * Clients can join a chat channel by providing the correct password.
 * Clients in the same chat channel can send messages to each other.
 */
public class ChatChannel {
    private final String ChannelName;
    private final String ChannelPassword;
    private final Set<ClientHandler> clientsSet;
    private final Set<SaveObserver> observers;

   ChatSaver chatSaverLocal;

    /**
     * Constructor for a ChatChannel with a given name and password.
     * @param name the name of the chat channel.
     * @param password the password for the chat channel.
     */
    public ChatChannel(String name, String password) {
        this.ChannelName = name;
        this.ChannelPassword = password;
        this.clientsSet = new HashSet<>();
        this.observers = new HashSet<>();
        this.chatSaverLocal = new ChatSaver(this);
        Server.logger.fine("Creating chat channel: " + name + " with password: " + password);

    }
    /**
     * Validates the password for the chat channel.
     * @param password the password to validate.
     * @return true if the password is correct, false otherwise.
     */
    public synchronized boolean validatePassword(String password) {

        return this.ChannelPassword.equals(password);
    }
    /**
     * Adds a client to the chat channel.
     * @param client the client to add.
     */
    public synchronized void addClient(ClientHandler client) {
        Server.logger.fine("Adding client: " + client.getName() + " to chat channel: " + this.ChannelName);
        boolean added = clientsSet.add(client);
        if(!added){
            //TODO throw exception or something like that to notify the client that he is already in the channel
            Server.logger.log(Level.SEVERE, "Client already in the channel or we have a problem in adding the client");
        }
    }

    /**
     * Removes a client from the chat channel.
     * @param client the client to remove.
     */
    public synchronized void removeClient(ClientHandler client) {
        boolean removed = clientsSet.remove(client);
        if(!removed){
            //TODO: throw exception or something like that to notify the client that he is not in the channel
            Server.logger.log(Level.SEVERE, "Client not in the channel or we have a problem in removing the client");
        }
    }

    /**
     * Broadcast Message to all Clients in the ChatChannel
     * @param message The message to be sent
     */
    public synchronized void broadcast(Message message) {
        for (ClientHandler client : clientsSet) {
                client.sendMessage(message);
        }
        notifyObservers(message);
    }
    private void notifyObservers(Message message) {
        for (SaveObserver observer : observers) {
            observer.update(ChannelName, message);
        }
    }
    //method to turn the string builder to later send to the client
    public StringBuilder getChatChannelHistory() {
        return ChatSaver.getChatHistory(this);
    }

    /**
     * Returns the name of the chat channel.
     * @return the name of the chat channel.
     */

    public Set<ClientHandler> getClients() {
        return clientsSet;
    }

    public String getName() {
        return ChannelName;
    }

    public void addObserver(ChatSaverObserver observer){
        observers.add(observer);
    }
    public void removeObserver(String observer){
        observers.remove(observer);
    }

}