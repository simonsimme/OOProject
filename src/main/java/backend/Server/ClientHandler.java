package backend.Server;
import backend.Messages.Client.ErrorResponse;
import backend.Messages.Client.MessageInChannel;
import backend.Messages.Message;
import backend.Messages.Server.MessageVisitorServer;
import backend.Messages.Server.ServerMessageVisitor;
import backend.Messages.UI.DisplayMessage;

import java.net.*;
import java.io.*;

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
    private ChatChannel currentChannel;

    /**
     * Constructor for a {@code ClientHandler} for a given client socket and server instance.
     * @param socket the client's socket connection.
     * @param server the server instance manging chat channels.
     */
    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.clientSocket = socket;

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
    private void handleError(Exception e) {
        System.out.println("Client handler exception: " + e.getMessage());
    }
    Message readMessage() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }
    private void processMessage(Message message) {
        try{
            ServerMessageVisitor handler = new MessageVisitorServer(this);
            message.accept(handler);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }

    }
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
        ChatChannel channel = null;
        try{
             channel = getChannel(channelName);

        }catch (IllegalArgumentException e){
            ErrorResponse error = new ErrorResponse(e.getMessage());
            sendMessage(error);
            return false;
        }

        boolean result = false;


        if(channel.validatePassword(password))
        {
            if(channel.getClients().contains(this)){
                ErrorResponse error = new ErrorResponse("You are already in this channel");
                sendMessage(error);
                result = false;
            }else {
                currentChannel = channel;
                currentChannel.addClient(this);
                result = true;
            }

        }
        else{
            ErrorResponse error = new ErrorResponse("Invalid password");
            sendMessage(error);
        }
        return result;
    }
    public boolean leaveChannel() {
        boolean result = false;
        if (currentChannel != null) {
            currentChannel.removeClient(this);
            // if we leave a channel then we set the current channel to null
            currentChannel = null;
            result = true;
        }
        else{
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
    public ChatChannel getCurrentChannel() {
        return currentChannel;
    }


    public boolean createChannel(String channelName, String password) {
        boolean result = false;
        if (server.getChannel(channelName) == null) {
            server.createChannel(channelName, password);
            result = true;

        server.createChannel(channelName, password);
        currentChannel = getChannel(channelName);
        currentChannel.addClient(this);
        } else {
            ErrorResponse error = new ErrorResponse("Channel name already taken");
            sendMessage(error);
        }
        return result;
    }
    public ChatChannel getChannel(String channelName) {
        ChatChannel channel = server.getChannel(channelName);
        if(channel != null){
            return channel;
        } else{
            throw new IllegalArgumentException("Channel does not exist");
        }
    }
}