package backend;
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
    private Socket clientSocket;
    //Input stream to receive messages from the client.
    private ObjectInputStream input;
    //Output stream to send messages to the client.
    private ObjectOutputStream output;
    //The server instance managing the chat channels.
    private Server server;
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
            while ((message = (Message) input.readObject()) != null) {
                message.accept(handler);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        } finally {
            closeConnections();
        }
    }
    private void closeConnections() {
        try {
            clientSocket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            System.out.println("Error closing client connection: " + e.getMessage());
        }
    }
    /**
     * Joins the client to a new chat channel. If the client was in a previous
     * channel, they are removed from it before joining a new chat channel.
     * @param channelName the name of the channel to join
     */
    /*public void joinChannel(String channelName) {
        if (currentChannel != null) {
            currentChannel.removeClient(this);
        }
        currentChannel = server.getOrCreateChannel(channelName);
        currentChannel.addClient(this);
        sendMessage(new Message("Joined channel: " + channelName, "Server", CommandType.MESSAGE));
    }*/
    public void leaveChannel() {
        if (currentChannel != null) {
            currentChannel.removeClient(this);
            sendMessage(new Message("Left channel " + currentChannel, "Server", CommandType.MESSAGE));
        }
        else {
            sendMessage(new Message("You are not in any channel to leave", "Server", CommandType.MESSAGE));
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
    public void setCurrentChannel(ChatChannel channel) {
        currentChannel = channel;
    }
    public Server getServer() {
        return server;
    }
}