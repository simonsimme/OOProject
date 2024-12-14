package Model.Server;

import Model.EncryptionLayer;
import Model.Server.saving.ChatSaverObserver;
import javax.crypto.SecretKey;
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
    private final SecretKey key;

    public static Logger logger = Logger.getLogger(Server.class.getName());
    // Initialize the logger and write to a file in the server directory
    //this will be used to debug the server
    static {
        try {
            // Create a FileHandler that writes log messages to a file
            FileHandler fileHandler = new FileHandler("./src/main/java/Model/Server/server.log", false);
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
            try {
                SecretKey key = EncryptionLayer.generateKey();
                thisServer = new Server(port, key);
                Server.logger.log(Level.FINE, "Server created on port: " + port);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return thisServer;
    }
    /**
     * Private constructor to initialize the server on the specific port.
     * Initializes the channels map and creates a default channel.
     *
     * @param port the port number the server will listen to.
     * @param key
     */
    private Server(int port, SecretKey key) {
        this.key = key;
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
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            System.out.println("Shutting down server...");

            try {
                this.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public SecretKey getKeys() {
        return key;
    }

    /**
     * Starts the server to listen to for incoming client connections.
     * Each client connection is handled by a thread using {@code ClientHandler}
     */
    public  void startListening() {
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
    public  void stop() throws IOException {
        isRunning = false; // Stop accepting new connections
        if (server != null && !server.isClosed()) {
            server.close(); // Close the server socket to break the accept() call
            thisServer = null; // Reset the server instance
        }
        this.deleteAllChannels();
        logger.log(Level.FINE,"Deleted all channels");
        this.deleteAllLogFiles();
        logger.log(Level.FINE,"Deleted all log files");
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
            ChatChannel channel = new ChatChannel(channelName, password);
            channels.put(channelName, channel);
            // the logic to add the observers to write to the txt files.
            ChatSaverObserver chatSaverObserver = new ChatSaverObserver(channel);
            channel.addObserver(chatSaverObserver);
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
    public synchronized void deleteAllChannels() {
        for (String channelName : channels.keySet()) {
            ChatChannel channel = channels.get(channelName);
            if (channel != null) {
                channel.removeObserver(channelName); // Clean up observers if necessary
                System.out.println("Channel " + channelName + " deleted.");
            }
        }
        channels.clear();
    }
    public synchronized void deleteAllLogFiles() {
        String folderPath = "./src/main/java/Model/Server/saving/logs";
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        System.err.println("Failed to delete file: " + file.getName());
                    }
                }
            }
        }
    }
}