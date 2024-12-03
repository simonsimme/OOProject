package backend.Server;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
    private volatile boolean isRunning;

    public static Logger logger = Logger.getLogger(Server.class.getName());
    // Initialize the logger and write to a file in the server directory
    //this will be used to debug the server
    static {
        try {
            // Create a FileHandler that writes log messages to a file
            FileHandler fileHandler = new FileHandler("./src/main/java/backend/Server/server.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger FileHandler.", e);
        }
    }

    /**
     * Creates a singleton instance of the Server with the specific port.
     * If the server instance already exist, it returns the existing instance.
     * @param port the port number the server will listen to.
     * @return the singleton instance of the Server
     */
    public static Server createServerInstance(int port) {
        if (thisServer == null) {
            thisServer = new Server(port);
            Server.logger.log(Level.FINE, "Server created on port: " + port);

        }
        return thisServer;
    }
    /**
     * Private constructor to initialize the server on the specific port.
     * Initializes the channels map and creates a default channel.
     * @param port the port number the server will listen to.
     */
    private Server(int port) {
        isRunning = true;
        channels = new HashMap<>();
        System.out.println("Started new server:");
        try {
            this.server = new ServerSocket(port);
            logger.log(Level.FINE, "Server socket is created on port: " + server.getLocalPort());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, e.getMessage());

        }
    }

    /**
     * Starts the server to listen to for incoming client connections.
     * Each client connection is handled by a thread using {@code ClientHandler}
     */
    public void startListening() {
        try {
            while (isRunning) {
                Server.logger.log(Level.FINE, "Waiting for clients...");
                System.out.println("Waiting for clients...");
                Socket clientSocket = server.accept();

                if(!isRunning) {
                    break;
                }

                System.out.println("New client connected: " + clientSocket.getLocalAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Stops the server to listening for incoming client connects.
     * sets {@code isRunning} to false when called
     * @throws IOException if an I/O error occurs while closing the server socket.
     */
    public void stop() throws IOException {
        isRunning = false; // Stop accepting new connections
        if (server != null && !server.isClosed()) {
            server.close(); // Close the server socket to break the accept() call
            thisServer = null; // Reset the server instance
            logger = null; // Reset the logger
        }
    }
    /**
     * Creates a new chat channel with the specific name and password
     * If a channel with the given name already exist, the method notifies
     * the user and does not create a new channel.
     * This method is synchronized to ensure thread-safe access to shared {@code channels} map.
     * @param channelName the name of the channel to be created. Must be unique.
     * @param password the password for the channel, used for authentication.
     */
    public synchronized void createChannel(String channelName, String password) {
        if (channels.containsKey(channelName)) {
            System.out.println("ChannelName taken, Try another one.");
            logger.log(Level.FINER, "ChannelName taken, Try another one.");
        } else {
            channels.put(channelName, new ChatChannel(channelName, password));
            logger.log(Level.FINE, "Channel created: " + channelName);
        }
    }

    /**
     * Retrieves the chat channel by its name.
     * @param channelName the name of the channel to get.
     * @return the {@code ChatChannel} object associated with the specific name.
     */
    public synchronized ChatChannel getChannel(String channelName) {
        return channels.get(channelName);
    }

    public synchronized Map<String, ChatChannel> getChannels() {
        return channels;
    }
}