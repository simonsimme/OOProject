package Model.Client;

import Model.Messages.Client.ClientMessage;
import Model.Messages.Server.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Handles communication between client, server and UI.
 */
public class ClientCommunicationManager implements Runnable{

    /**
     * Used for communication with the server.
     */
    private Socket socket;
    /**
     * Input stream, ClientMessages get sent to this stream from the server.
     */
    private ObjectInputStream in;
    /**
     * Output stream, ServerMessages get sent to this stream, these are the messages that the server receives.
     */
    private ObjectOutputStream out;
    /**
     * Ip address of the server host.
     */
    private final String host;

    /**
     * Port that the socket should connect to.
     */
    private final int port;
    /**
     * Handler for input messages, see Visitor pattern.
     */
    private final ClientVisitor visitor;

    /**
     * Only Constructor
     * @param address The address of the server to connect to.
     * @param port The port of the server to connect to.
     * @param channelGroup Passed reference.
     * @param observers Passed reference.
     */
    public ClientCommunicationManager(String address, int port, ClientChannelRecord channelGroup, List<ClientObserver> observers){

        this.visitor = new ClientVisitor(channelGroup,observers);

        this.host = address;
        this.port = port;
        System.out.println("Connecting to server" + address);

        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Client implements the Runnable interface. The run() method continually reads messages from the server
     * and run handleMessage() on them.
     */
    @Override
    public void run()
    {
        Object message;
        try {
            while (true)
            {
                message = in.readObject();
                if(message instanceof ClientMessage ) {
                    handleMessage((ClientMessage) message);
                }
                else{
                    throw new IllegalArgumentException("Yousef & Gunnar are unsure about this");

                }
            }
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {

            e.printStackTrace();

        }
    }

    /**
     *
     * @param message The message to send to the ui.
     */
    public void handleMessage(ClientMessage message) {
        message.accept(visitor);
    }

    /**
     * Sends a request to the server to create a new channel.
     * @param userName Name of the user that sent the request. (current user)
     * @param channelName Name of the new channel.
     * @param password The password of the new channel.
     */
    public void createChannel(String userName,String channelName, String password)
    {
        System.out.println("Sending create channel command");
        ServerMessage message = new CreateChannelCommand(userName,channelName,password);
        sendMessageToServer(message);
    }

    /**
     * Sends a request to the server that the given user wants to send a string message in the given channel.
     * @param user Name of the user that sent the request. (current user)
     * @param channel Name of the channel that the message is to be sent in.
     * @param messageString Actual message string.
     */
    public void sendMessage(String user, String channel, String messageString, boolean isServerMessage)
    {
        ServerMessage message =
                new SendMessageInChannelCommand(user, channel, messageString, isServerMessage);

        sendMessageToServer(message);
    }

    /**
     * Sends a request to the server that the given user wants to join a channel. This request may be denied by
     * the server if the password supplied is incorrect.
     * @param userName Name of the user that sent the request (currentUser).
     * @param channelName Name of the channel that is attempted to be joined.
     * @param password Password, this has to match with the server's record of the password for the channel for
     *                 the request to be accepted.
     */
    public void joinChannel(String userName,String channelName,String password){
        try{
            ServerMessage message = new JoinChannelCommand(userName,channelName,password);
            sendMessageToServer(message);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }

    }
    public void getChannelHistory(String userName, String channelName){
        ServerMessage message = new RetrieveChatHistoryCommand(userName, channelName);
        sendMessageToServer(message);
    }
    /**
     * Sends a request to the server that the given user wants to leave a channel.
     * @param userName Name of the user that sent the request (currentUser).
     * @param channelName Name of the channel that is attempted to be left.
     */
    public void leaveChannel(String userName, String channelName){
        ServerMessage message = new LeaveChannelCommand(userName, channelName);
        sendMessageToServer(message);
    }

    /**
     * Sends a message to the server that the user is disconnected.
     * Sends a {@code LeaveChannelCommand} to notify the server to remove the username from channel
     * @param userName The user to be disconnected.
     * @param channelName The channel name that user gets disconnected from.
     */
    public void disconnect(String userName, String channelName) {
        String disconnected = "has disconnected from the channel";
        ServerMessage disconnect = new SendMessageInChannelCommand(userName, channelName, disconnected, true);
        sendMessageToServer(disconnect);

        ServerMessage message = new LeaveChannelCommand(userName, channelName);
        sendMessageToServer(message);
    }
    /**
     * Helper method, sends given message to the server's socket's inputStream.
     * @param message being sent to the server
     */
    public void sendMessageToServer(ServerMessage message){
        try{
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            e.printStackTrace();
            throw new IllegalArgumentException("Error sending message: " + e.getMessage());
        }
    }
}
