package backend;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Server class implements a basic server that listens for incoming client
 * connections, handles chat channels and communication between clients.
 */
public class Server {
    //ServerSocket will listen for incoming connections.
    private ServerSocket server = null;
    //Singleton instance of server.
    private static Server thisServer = null;
    //A map of active chat channels, identified by their names.
    private final Map<String, ChatChannel> channels;

    /**
     * Creates a singleton instance of the Server with the specific port.
     * If the server instance already exist, it returns the existing instance.
     * @param port the port number the server will listen to.
     * @return the singleton instance of the Server
     */
    public static Server createServerInstance(int port) {
        if (thisServer == null) {
            thisServer = new Server(port);
        }
        return thisServer;
    }
    /**
     * Private constructor to initialize the server on the specific port.
     * Initializes the channels map and creates a default channel.
     * @param port the port number the server will listen to.
     */
    private Server(int port) {
        channels = new HashMap<>();
        channels.put("yes", new ChatChannel("yes"));
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts the server to listen to for incoming client connections.
     * Each client connection is handled by a thread using {@code ClientHandler}
     */
    public void startListening() {
        try {
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

    /**
     * Get an existing Chat Channel by its name or creates a new one
     * if none exist.
     * @param channelName the name of the chat channel
     * @return the chat channel with the name
     */
    public synchronized ChatChannel getOrCreateChannel(String channelName) {
        System.out.println("Channel name: " + channelName);
        return channels.computeIfAbsent(channelName, ChatChannel::new);
    }
}