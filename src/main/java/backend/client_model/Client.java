package backend.client_model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import Controller.UIController;
import backend.CommandType;
import backend.Message;

/**
 * The client class handles the logic behind the client communication with the server. This includes sending
 * messages to the server and receiving messages from the server.
 * TODO: Implement client in a way that satisfies: Single Responsibility Principle, Open, Closed Principle
 */
public class Client implements Runnable, ClientSubject {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String host;
    private int port;
    private String sender = "Client";
    private List<ClientObserver> observers = new ArrayList<>();
    /**
     * Client's only constructor, requires the adress to connect to and a port.
     * @param adress The adress that the Socket connects to. (Has to be "localhost" for now)
     * @param port The port that the Socket connects to. (Has to match with server port)
     * @throws IOException TODO: Handle exception
     */
    public Client(String adress, int port) throws IOException {
        this.host = adress;
        this.port = port;

        System.out.print("Connecting to server...");
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

    }
    public void setNickName(String name)
    {
        sender = name;
    }

    /**
     * Send the given String to the server through ObjectOutputStream out.
     * TODO: Implement functionality for sending messages of different types (ex. send join channel request)
     * TODO: Handle exception
     * @param messageString Input string, from interface
     * @throws IOException
     */
    public void sendMessage(String messageString) throws IOException
    {
        Message message = new Message(messageString,sender );
        //uiController.showTextinView(message);
        out.writeObject(message);
        out.flush();
    }
    public void sendMessage(Message msg) throws IOException
    {
        //uiController.showTextinView(message);
        out.writeObject(msg);
        out.flush();
    }
    public String getName()
    {
        return sender;
    }

    /**
     * Client implements the Runnable interface. The run() method continually reads messages from the server.
     * TODO:Read Messages of different types
     */
    @Override
    public void run()
    {
        Message message;
        try {
            while ((message = (Message) in.readObject()) != null)
            {
                handleMessage(message);
                notifyObservers(message);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO:
     * Display message in UI
     * Handle Different Kind of Messages, Differentiate between Server & User Messages
     *
     * @param message The message to display.
     * @throws IOException TODO: Handle exception
     */
    private void handleMessage(Message message) throws IOException {
        System.out.println(message.getTimestamp() + ". " + message.getSender() + " : " + message.getContent());
        //uiController.showTextinView(message);
        //Display message in UI
    }

    @Override
    public void attach(ClientObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(ClientObserver observer) {
        observers.remove(observer);
    }



    @Override
    public void notifyObservers(Message message) {
        for (ClientObserver observer : observers) {
            observer.update(message);
        }
    }


}