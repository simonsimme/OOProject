package backend;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private ServerSocket server = null;
    private static Server thisServer = null;
    private Map<String, ChatChannel> channels;

    public static Server createServerInstance(int port) {
        if (thisServer == null) {
            return new Server(port);
        } else {
            return thisServer;
        }
    }

    private Server(int port) {
        channels = new HashMap<>();
        //test channel
        channels.put("yes",new ChatChannel("yes"));
        try {
            this.server = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for clients...");
                Socket clientSocket = server.accept();
                System.out.println("New client connected: " + clientSocket.getLocalAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        Server server = Server.createServerInstance(8070);
    }

    public synchronized ChatChannel getOrCreateChannel(String channelName) {
        return channels.computeIfAbsent(channelName, ChatChannel::new);
    }
}