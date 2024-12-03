package backend.Server;
import backend.Messages.Client.ErrorResponse;
import backend.Messages.Client.MessageInChannel;
import backend.Messages.Message;
import backend.Messages.Server.MessageVisitorServer;
import backend.Messages.Server.ServerMessageVisitor;
import backend.Messages.UI.DisplayMessage;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class handles communication with a single client connected to the server.
 * Each {@code ClientHandler} instance runs its own thread, managing:
 * <li>Receiving messages from the client</li>
 * <li>Sending messages to the client</li>
 * <li>Joining chat channels</li>
 * <li>Broadcast messages to other clients in the same channel</li>
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
    private List<ChatChannel> channels;





    /**
     * Constructor for a {@code ClientHandler} for a given client socket and server instance.
     * @param socket the client's socket connection.
     * @param server the server instance manging chat channels.
     */
    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.clientSocket = socket;
        this.channels = new ArrayList<>();



        try {
            this.input = new ObjectInputStream(clientSocket.getInputStream());
            this.output = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error setting up input/output stream: " + e.getMessage());
        }
    }
    /**
     * Listens for incoming messages from the clients and processes then.
     * <li>MESSAGE: execute SendMessageCommand to send message to the clients</li>
     * Ensures resources are cleaned up when the client disconnects.
     */
    @Override
    public void run() {
        try {
            while (input != null) {
                Message message = readMessage();
                if (message != null) {
                    processMessage(message);
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
            handleError(e);
        } finally {
            closeConnections();
        }
    }
    /**
     * Handles exceptions that occur during message processing. At this time its only printing the error message
     * @param e the exception that occurred.
     */
    private void handleError(Exception e) {
        System.out.println("Client handler exception: " + e.getMessage());
    }
    /**
     * Reads a message from the client.
     * @return the message received from the client.
     */
    private Message readMessage() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }
    /**
     * Processes a message received from the client.
     * @param message the message to process.
     */
    private void processMessage(Message message) {
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
    public void closeConnections() {
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
     * Joins the client to a new chat channel. If the client was in a previous
     * channel, they are removed from it before joining a new chat channel.
     * @param channelName the name of the channel to join
     */
    public boolean joinChannel(String channelName, String password) {       
        ChatChannel channel = getChannel(channelName);
        boolean result = false;

        if (channel == null) {
            ErrorResponse error = new ErrorResponse("Channel does not exist--");

            sendMessage(error);
            return false;
        }
        
        else if(channel.validatePassword(password))
        {
            if(channel.getClients().contains(this)){
                ErrorResponse error = new ErrorResponse("You are already in this channel");
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
            ErrorResponse error = new ErrorResponse("Invalid password");
            sendMessage(error);
        }
        return result;
    }

    /**
     * Leaves the chat channel if the client is in that one.
     * If the client is not in the channel, nothing happens.
     * Uses booleam to indicate if the client was removed from the channel
     * @param channelName the name of the channel to leave
     */
    public boolean leaveChannel(String channelName) {
        ChatChannel channel = getChannel(channelName);
        boolean result = false;

        if (channel != null && channels.contains(channel))
        {
            channel.removeClient(this);
            result = true;
        }
        else
        {
            //TODO meybe also send a message to the client that you
            // canot leave a channel if you are not in one
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
     * returns the chatChannel thats in the last index of the list of channels
     * if the list is empty it returns null
     * @return the chatChannel that is in the last index of the list of channels
     *
     * Look into the function, currently only used in testing might be Removed
     * */
    public ChatChannel getCurrentChannel() {
        return channels.isEmpty() ? null : channels.get(channels.size() - 1);
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
            ErrorResponse error = new ErrorResponse("Channel name already taken");
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
        ChatChannel channel = server.getChannel(channelName);
        if(channel != null){
            return channel;
        } else{
            throw new IllegalArgumentException("Channel does not exist");
        }
    }
}