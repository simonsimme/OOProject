package Main;

import Controller.UIClientObserver;
import Controller.UIController;
import View.components.Factorys.StandardViewFactory;
import View.components.Factorys.ViewFactory;
import View.components.IView;
import Model.Client.Client;
public class ClientApplication {


    public static void main(String[] args) {
        ViewFactory viewFactory = new StandardViewFactory();
        IView view = viewFactory.createView();
        Client client = new Client("localhost", 1234);
        UIController ui = new UIController(view, client);
        UIClientObserver observer = new UIClientObserver(view);
        client.attach(observer);
    }
}
