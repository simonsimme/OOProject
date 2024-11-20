package Main;

import View.View;
import Controller.UIController;
import backend.Server;
import backend.client_model.Client;

import java.io.IOException;

public class ChatApplication {
    Client client;
    Client client2;
    UIController uiController1;
    UIController uiController2;

    public static void main(String[] args) throws IOException {
        ChatApplication chatApplication = new ChatApplication();


        // Start the server in a separate thread
        Thread serverThread = new Thread(() -> {
            Server server = Server.createServerInstance(1234);
            server.startListening();
        });
        serverThread.start();

        // Wait a moment to ensure the server is up and running
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chatApplication.startClients();

    }
    public void startClients() throws IOException {
        // Create and run the first client with its own view
        View view1 = new View();
        client = new Client("localhost", 1234);
         uiController1 = new UIController(view1, this, client);
        new Thread(client).start();

        // Create and run the second client with its own view
        View view2 = new View();
        client2 = new Client("localhost", 1234);
         uiController2 = new UIController(view2, this, client2);
        new Thread(client2).start();
        client.attach(uiController1);
        client.attach(uiController2);
        client2.attach(uiController2);
        client2.attach(uiController1);



    }
    public void sendFromClients(String msg, Client ref) throws IOException {
        Message message = new Message(msg,ref.getName());
        ref.sendMessage(msg);
        uiController1.showTextinView(message);
        uiController2.showTextinView(message);
    }
}