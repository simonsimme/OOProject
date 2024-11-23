package backend.client_model;

import backend.Messages.*;
import backend.Messages.Client.ClientMessage;
import backend.Messages.Server.CreateChannelCommand;
import backend.Messages.Server.JoinChannelCommand;
import backend.Messages.Server.LeaveChannelCommand;
import backend.Messages.Server.SendMessageInChannelCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Handles communication between client, server and UI.
 */
public class ClientCommunicationManager implements Runnable{


    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String host;
    private int port;
    private ClientVisitor visitor;

    public ClientCommunicationManager(String adress, int port, ClientChannelGroup channelGroup, List<ClientObserver> observers){

        this.visitor = new ClientVisitor(channelGroup,observers);

        this.host = adress;
        this.port = port;

        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            //TODO : Handle exception
        }
    }

    /**
     * Client implements the Runnable interface. The run() method continually reads messages from the server.
     */
    @Override
    public void run()
    {
        Object message;
        try {
            while ((message = in.readObject()) != null)
            {
                if(message instanceof ClientMessage) handleMessage((ClientMessage) message);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param message The message to send to the ui.
     */
    public void handleMessage(ClientMessage message) {
       // notifyObservers(message);
        message.accept(visitor);
    }


    public void createChannel(String userName,String channelName, String password)
    {
        Message message = new CreateChannelCommand(userName,channelName,password);
        sendMessageToServer(message);

    }

    public void sendMessage(String user, String channel, String messageString)
    {
        Message message =
                new SendMessageInChannelCommand(user, channel, messageString);
        sendMessageToServer(message);
    }

    public void joinChannel(String userName,String channelName,String password){
        Message message = new JoinChannelCommand(userName,channelName,password);
        sendMessageToServer(message);
    }
    public void leaveChannel(String userName, String channelName){
        Message message = new LeaveChannelCommand(userName, channelName);
        sendMessageToServer(message);
    }


    private void sendMessageToServer(Message message){
        try{
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            e.printStackTrace();
            //TODO : Handle exception
        }
    }
}
