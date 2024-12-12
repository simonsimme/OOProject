package Main;

import Controller.UIClientObserver;
import Controller.UIController;
import Model.EncryptionLayer;
import View.components.Factorys.StandardViewFactory;
import View.components.Factorys.ViewFactory;
import View.components.IView;
import Model.Client.Client;

import javax.crypto.SecretKey;

public class ClientApplication {


    public static void main(String[] args) throws Exception {
       SecretKey key = EncryptionLayer.generateKey();
        ViewFactory viewFactory = new StandardViewFactory();
        IView view = viewFactory.createView();
        Client client = new Client("localhost", 1234);
        //TODO make so the client get the KEY from the server and pass it to ui and observer
        UIController ui = new UIController(view, client, key);
        UIClientObserver observer = new UIClientObserver(view, key, client);
        client.attach(observer);
    }
}
