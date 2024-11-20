package backend;

import java.util.HashSet;
import java.util.Set;

public class ChatChannel {
    private String name;
    private Set<ClientHandler> clients;

    public ChatChannel(String name) {
        this.name = name;
        this.clients = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public synchronized void addClient(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public synchronized void broadcast(Message message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if(client.equals(sender)) {
                client.sendMessage(message);
            }
        }
    }
}