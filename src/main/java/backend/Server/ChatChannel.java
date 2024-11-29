package backend.Server;

import backend.Messages.Message;
import java.util.HashSet;
import java.util.Set;

public class ChatChannel {
    private final String name;
    private final String password;
    private final Set<ClientHandler> clients;


    public ChatChannel(String name, String password) {
        this.name = name;
        this.password = password;
        this.clients = new HashSet<>();
    }

    public synchronized boolean validatePassword(String password) {

        return this.password.equals(password);
    }
    public synchronized void addClient(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
        boolean removed = clients.remove(client);
        if(!removed){
            //TODO: throw exception or something like that to notify the client that he is not in the channel
        }
    }

    /**
     * Broadcast Message to all Clients in the ChatChannel
     * @param message The message to be sent
     */
    public synchronized void broadcast(Message message) {
        for (ClientHandler client : clients) {
                client.sendMessage(message);
        }
    }

    public Set<ClientHandler> getClients() {
        return clients;
    }

    public String getName() {
        return name;
    }
}