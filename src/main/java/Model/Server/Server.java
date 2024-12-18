package Model.Server;

import Model.EncryptionLayer;
import Model.Server.saving.ChatSaverObserver;
import javax.crypto.SecretKey;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
    private ServerSocket serverSocket = null;
    //Singleton instance of server.
    private static Server thisServerInstance = null;

    //A map of active chat channels, identified by their names.
    private final Set<ChatChannel> channelSet;
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
        if (thisServerInstance == null) {
            try {
                SecretKey key = EncryptionLayer.generateKey();
                thisServerInstance = new Server(port, key);
                Server.logger.log(Level.FINE, "Server created on port: " + port);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return thisServerInstance;
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
        channelSet = new HashSet<ChatChannel>();
        System.out.println("Started new server:");
        try {
            this.serverSocket = new ServerSocket(port);
            logger.log(Level.FINE, "Server socket is created on port: " + serverSocket.getLocalPort());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, e.getMessage());

        }
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
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
                Socket clientSocket = serverSocket.accept();

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
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close(); // Close the server socket to break the accept() call
            thisServerInstance = null; // Reset the server instance
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
        if (channelSet.contains(channelName)) {
            System.out.println("ChannelName taken, Try another one.");
            logger.log(Level.FINER, "ChannelName taken, Try another one.");
        } else {
            ChatChannel channel = new ChatChannel(channelName, password);
            channelSet.add(channel);
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
        for (ChatChannel channel : channelSet) {
            if (channel.getName().equals(channelName)) {
                return channel;
            }
        }
        return null; // Return null if the channel is not found

    }
    public synchronized void deleteAllChannels() {
        for (ChatChannel channel : channelSet) {
            if (channel != null) {
                channel.removeObserver(channel); // Clean up observers if necessary
            }
        }
        channelSet.clear();
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