// ChatApplication.java
package Main;

import Controller.UIClientObserver;
import Controller.UIController;
import Model.Client.Client;
import Model.Server.Server;
import View.components.Factorys.StandardViewFactory;
import View.components.Factorys.ViewFactory;
import View.components.IView;
import javax.crypto.SecretKey;

public class ChatApplication {
    Client client;
    Client client2;
    UIController uiController1;
    UIController uiController2;
    static Server server;

    public static void main(String[] args) throws Exception {
        ChatApplication chatApplication = new ChatApplication();

        // Start the server in a separate thread
        Thread serverThread = new Thread(() -> {
             server = Server.createServerInstance(1234);
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
        SecretKey key = server.getKeys();
        // Create and run the first client with its own view
        ViewFactory viewFactory1 = new StandardViewFactory();
        IView view1 = viewFactory1.createView();
        client = new Client("localhost", 1234);

        UIClientObserver observer = new UIClientObserver(view1,key, client);
        uiController1 = new  UIController( view1, client,key);


        // Create and run the second client with its own view
        ViewFactory viewFactory2 = new StandardViewFactory ();
        IView view2 = viewFactory2.createView();
        client2 = new Client("localhost", 1234);

        UIClientObserver observer2 = new UIClientObserver(view2,key, client2);
        uiController2 = new UIController(view2, client2,key);

        client.attach(observer);
        client2.attach(observer2);
    }
}