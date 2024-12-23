package Model.Server;

import Model.Messages.Client.ErrorResponse;
import Model.Messages.Message;
import Model.Messages.Server.ServerMessage;
import Model.Messages.Server.ServerMessageVisitor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles communication with a single client connected to the server.
 * Each {@code ClientHandler} instance runs its own thread to manage the client's interaction
 * with the server. It is responsible for receiving and sending messages, joining chat channels,
 * broadcasting messages, and handling errors.
 */
public class ClientHandler extends Thread {
    //The socket represents the client's connection.
    private final Socket clientSocket;
    //Input stream to receive messages from the client.
    private ObjectInputStream input;
    //Output stream to send messages to the client.
    private ObjectOutputStream output;
    //The server instance managing the chat channels.
    private final Server server;
    //Current chat channel the client is joined in.
    private final List<ChatChannel> channels;

    //we rewrite and create only one error object to lower the dependency between the classes
    private ErrorResponse error;

    /**
     * Constructor for a {@code ClientHandler} for a given client socket and server instance.
     *
     * @param clientSocket the client's socket connection
     * @param server the server instance managing chat channels
     */
    public ClientHandler(Socket clientSocket, Server server) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.channels = new ArrayList<>();

        try {
            this.input = new ObjectInputStream(this.clientSocket.getInputStream());
            this.output = new ObjectOutputStream(this.clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error setting up input/output stream: " + e.getMessage());
        }
    }

    /**
     * Listens for incoming messages from the client and processes them.
     * It handles different types of messages, such as sending a message to other clients
     * or performing channel management tasks (joining and leaving channels).
     * Ensures resources are cleaned up when the client disconnects.
     */
    @Override
    public void run() {
        try {
            while (input != null) {
                ServerMessage message = readMessage();
                if (message != null) {
                    processMessage(message);
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
            //client disconnected
            handleError(e);
        } finally {
            closeConnections();
        }
    }

    /**
     * Handles exceptions that occur during message processing. At this time its only printing the error message
     * @param error the exception that occurred.
     */
    private void handleError(Exception error) {
        System.out.println("Client handler exception: " + error.getMessage());
    }
    /**
     * Reads a message from the client.
     * @return the message received from the client.
     */
    private ServerMessage readMessage() throws IOException, ClassNotFoundException {
        return (ServerMessage) input.readObject();
    }
    /**
     * Processes a message received from the client.
     * @param message the message to process.
     */
    private void processMessage(ServerMessage message) {
        try{
            ServerMessageVisitor handler = new MessageVisitorServer(this);
            message.accept(handler);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }

    }
    /**
     * Closes the input/output streams and the client socket connection.
     */
    private void closeConnections() {
        // Ensure the input/output streams are closed properly
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }

        } catch (IOException e) {
            System.out.println("Error closing connections: " + e.getMessage());
        }
    }

    /**
     * Joins the client to a new chat channel. If the client was already in another channel,
     * they are removed from the old channel before joining the new one.
     *
     * @param channelName the name of the channel to join
     * @param password the password for the channel
     * @return true if the client successfully joined the channel, false otherwise
     */
    public boolean joinChannel(String channelName, String password) {       
        ChatChannel channel = this.getChannel(channelName);
        boolean result = false;

        if (channel == null) {
            this.error = new ErrorResponse("Channel does not exist");
            sendMessage(error);
            return false;
        }
        
        else if(channel.validatePassword(password))
        {
            if(channel.getClients().contains(this)){
                this.error = new ErrorResponse("You are already in this channel");
                sendMessage(error);
                result = false;
            }else {
                channels.add(channel);
                channel.addClient(this);
                result = true;
            }
        }
        else
        {
            this.error = new ErrorResponse("Invalid password");
            sendMessage(error);
        }
        return result;
    }

    /**
     * Leaves the chat channel if the client is currently in that channel.
     *
     * @param channelName the name of the channel to leave
     * @return true if the client successfully left the channel, false otherwise
     */
    public boolean leaveChannel(String channelName) {
        ChatChannel channel = getChannel(channelName);
        boolean result = false;

        if (channel != null && channels.contains(channel))
        {
            channel.removeClient(this);
            result = true;
        }
        return result;
    }

    /**
     * Sends a message to the client.
     * @param message the {@code Message} object to send
     */
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    /**
     * Creates a new chat channel with the given name and password.
     * Adds the client to the new channel.
     * returns true if the channel was created successfully
     * @param channelName the name of the new channel
     * @param password the password for the new channel
     */
    public boolean createChannel(String channelName, String password) {
        boolean result = false;

        if (server.getChannel(channelName) == null)
        {
            server.createChannel(channelName, password);
            result = true;
            ChatChannel newChannel = server.getChannel(channelName);
            channels.add(newChannel);
            newChannel.addClient(this);
        }
        else
        {
            this.error = new ErrorResponse("Channel name already taken");
            sendMessage(error);
        }
        return result;
    }

    /**
     * Returns the chat channel with the given name.
     * @param channelName the name of the channel to get
     * @return the chat channel with the given name
     */
    public ChatChannel getChannel(String channelName) {
        return server.getChannel(channelName);
    }
    /**
     * returns the chatChannel thats in the last index of the list of channels
     * if the list is empty it returns null
     * @return the chatChannel that is in the last index of the list of channels
     * Look into the function, currently only used in testing might be Removed
     * */
    public ChatChannel getCurrentChannel() {
        return channels.isEmpty() ? null : channels.getLast();
    }
}