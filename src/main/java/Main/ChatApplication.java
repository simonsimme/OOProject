// ChatApplication.java
package Main;

import Controller.UIClientObserver;
import View.components.Factorys.StandardViewFactory;
import View.components.Factorys.ViewFactory;
import View.components.IView;
import Controller.UIController;
import Model.Server.Server;
import Model.Client.Client;

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
        UIClientObserver observer = new UIClientObserver(view1);

        uiController1 = new  UIController( view1, client);


        // Create and run the second client with its own view
        ViewFactory viewFactory2 = new StandardViewFactory ();
        IView view2 = viewFactory2.createView();
        client2 = new Client("localhost", 1234);
        UIClientObserver observer2 = new UIClientObserver(view2);

         uiController2 = new UIController(view2, client2);
        //new Thread(client2).start(); Thread startas i client nu

        //TODO: Add so the client only attaches its own uicontroller the client should be the one who recives messages
        client.attach(observer);
      //  client.attach(uiController2);
        client2.attach(observer2);
       //    client2.attach(uiController1);
    }//e
}