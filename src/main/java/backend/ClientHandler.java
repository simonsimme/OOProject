package backend;
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
class ClientHandler extends Thread {
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
     * <li>If the messages starts with {@code /join}, the clients switches to the specific channel</li>
     * <li>If the client is in a channel, broadcasts messages to the chat channel</li>
     * Ensures resources are cleaned up when the client disconnects.
     */
    @Override
    public void run() {
        try {
            Message message;
            while ((message = (Message) input.readObject()) != null) {

                sendMessage(message); // just for testing
                //TODO Måster Fixas, Går ej igenom channelNull atm, Och kollar bara /join

                if (message.getContent().startsWith("/join ")) {
                    String channelName = message.getContent().substring(6);
                    joinChannel(channelName);
                } else if (currentChannel != null) {
                    currentChannel.broadcast(message, this);
                }
            }
            System.out.println("Client disconnected.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        } finally {
            try {
                if (currentChannel != null) {
                    currentChannel.removeClient(this);
                }
                clientSocket.close();
                input.close();
                output.close();
            } catch (IOException e) {
                System.out.println("Error closing client connection: " + e.getMessage());
            }
        }
    }

    /**
     * Joins the client to a new chat channel. If the client was in a previous
     * channel, they are removed from it before joining a new chat channel.
     * @param channelName the name of the channel to join
     */
    private void joinChannel(String channelName) {
        if (currentChannel != null) {
            currentChannel.removeClient(this);
        }
        currentChannel = server.getOrCreateChannel(channelName);
        currentChannel.addClient(this);
        sendMessage(new Message("Joined channel: " + channelName, "Server"));
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
}