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

    public boolean validatePassword(String password) {
        return this.password.equals(password);
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

    public Set<ClientHandler> getClients() {
        return clients;
    }
}