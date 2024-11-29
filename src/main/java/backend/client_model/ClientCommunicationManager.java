package backend.client_model;

import backend.Messages.Client.ClientMessage;
import backend.Messages.Client.ErrorResponse;
import backend.Messages.Server.*;
import backend.Messages.UI.UpdateChannels;

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
     * Inputstream, ClientMessages get sent to this stream from the server.
     */
    private ObjectInputStream in;
    /**
     * Outputstream, ServerMessages get sent to this stream, these are the messages that the server receives.
     */
    private ObjectOutputStream out;
    /**
     * Ip adress of the server host.
     */
    private String host;

    /**
     * Port that the socket should connect to.
     */
    private int port;
    /**
     * Handler for input messages, see Visitor pattern.
     */
    private ClientVisitor visitor;

    /**
     * Only Constructor
     * @param adress The adress of the server to connect to.
     * @param port The port of the server to connect to.
     * @param channelGroup Passed reference.
     * @param observers Passed reference.
     */
    public ClientCommunicationManager(String adress, int port, ClientChannelRecord channelGroup, List<ClientObserver> observers){

        this.visitor = new ClientVisitor(channelGroup,observers);

        this.host = adress;
        this.port = port;
        System.out.println("Connecting to server" + adress);

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
                if(message instanceof ClientMessage) handleMessage((ClientMessage) message);
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
    public void sendMessage(String user, String channel, String messageString)
    {
        ServerMessage message =
                new SendMessageInChannelCommand(user, channel, messageString);
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
     * Helper method, sends given message to the server's socket's inputStream.
     * @param message
     */
    private void sendMessageToServer(ServerMessage message){
        try{
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            e.printStackTrace();
            throw new IllegalArgumentException("Error sending message: " + e.getMessage());
        }
    }
}
