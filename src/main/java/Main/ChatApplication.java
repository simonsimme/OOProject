// ChatApplication.java
package Main;

import Controller.UIClientObserver;
import Model.EncryptionLayer;
import View.components.Factorys.StandardViewFactory;
import View.components.Factorys.ViewFactory;
import View.components.IView;
import Controller.UIController;
import Model.Server.Server;
import Model.Client.Client;

import javax.crypto.SecretKey;

public class ChatApplication {
    Client client;
    Client client2;
    UIController uiController1;
    UIController uiController2;

    public static void main(String[] args) throws Exception {
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

    public void startClients() throws Exception {
        // Create and run the first client with its own view
        SecretKey key = EncryptionLayer.generateKey();
        ViewFactory viewFactory1 = new StandardViewFactory();
        IView view1 = viewFactory1.createView();
        client = new Client("localhost", 1234);
        UIClientObserver observer = new UIClientObserver(view1,key);

        uiController1 = new  UIController( view1, client,key);


        // Create and run the second client with its own view
        ViewFactory viewFactory2 = new StandardViewFactory ();
        IView view2 = viewFactory2.createView();
        client2 = new Client("localhost", 1234);
        UIClientObserver observer2 = new UIClientObserver(view2,key);

         uiController2 = new UIController(view2, client2,key);
        //new Thread(client2).start(); Thread startas i client nu

        //TODO: Add so the client only attaches its own uicontroller the client should be the one who recives messages
        client.attach(observer);
      //  client.attach(uiController2);
        client2.attach(observer2);
       //    client2.attach(uiController1);
    }//e
}