// ChatApplication.java
package Main;

import View.DecoderViewFactory;
import View.StandardViewFactory;
import View.ViewFactory;
import View.IView;
import Controller.UIController;
import backend.Messages.UI.DisplayMessage;
import backend.Server.Server;
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
        ViewFactory viewFactory1 = new StandardViewFactory();
        IView view1 = viewFactory1.createView();
        client = new Client("localhost", 1234);

         uiController1 = new  UIController( view1, client);
        //new Thread(client).start(); Thread startas i client nu


        // Create and run the second client with its own view
        ViewFactory viewFactory2 = new StandardViewFactory ();
        IView view2 = viewFactory2.createView();
        client2 = new Client("localhost", 1234);

         uiController2 = new UIController(view2, client2);
        //new Thread(client2).start(); Thread startas i client nu
        //TODO: Add so the client only attaches its own uicontroller the client should be the one who recives messages
        client.attach(uiController1);
        //client.attach(uiController2);
        client2.attach(uiController2);
        //client2.attach(uiController1);
    }





    public void sendFromClients(String msg, Client ref) throws IOException {
        //Message message = new Message(msg,ref.getUserName());
        ref.sendMessage(msg);
        uiController1.showTextinView(new DisplayMessage(ref.getUserName(),msg));
        uiController2.showTextinView(new DisplayMessage(ref.getUserName(), msg));
    }

}