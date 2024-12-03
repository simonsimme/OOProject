package Main;

import Controller.UIController;
import Move.Factorys.StandardViewFactory;
import Move.Factorys.ViewFactory;
import Move.IView;
import Model.client_model.Client;
public class ClientApplication {


    public static void main(String[] args) {
        ViewFactory viewFactory = new StandardViewFactory();
        IView view = viewFactory.createView();
        Client client = new Client("localhost", 1234);
        UIController ui = new UIController(view, client);
        client.attach(ui);
    }
}
