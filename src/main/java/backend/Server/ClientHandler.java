package backend.Server;
import backend.Messages.Message;
import backend.Messages.Server.MessageVisitorServer;
import backend.Messages.Server.ServerMessageVisitor;

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
     * Constructor for a {@code Clienthandler} for a given client socket and server instance.
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
            Message message;
            ServerMessageVisitor handler = new MessageVisitorServer(this);
            while (input != null && (message = (Message) input.readObject()) != null) {
                message.accept(handler);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        } finally {
            closeConnections();
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
    public void joinChannel(String channelName, String password) {
        ChatChannel channel = getChannel(channelName);
        if(channel.validatePassword(password))
        {
            currentChannel = server.getOrCreateChannel(channelName, password);
            currentChannel.addClient(this);
        }

    }
    public void leaveChannel() {
        if (currentChannel != null) {
            currentChannel.removeClient(this);
        }
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

    public void createChannel(String channelName, String password) {
        server.createChannel(channelName, password);
        currentChannel = getChannel(channelName);
        getChannel(channelName).addClient(this);
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