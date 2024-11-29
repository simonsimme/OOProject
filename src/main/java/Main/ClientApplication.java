package Main;

import Controller.UIController;
import Model.View.Factorys.StandardViewFactory;
import Model.View.Factorys.ViewFactory;
import Model.View.IView;
import backend.client_model.Client;
public class ClientApplication {


    public static void main(String[] args) {
        ViewFactory viewFactory = new StandardViewFactory();
        IView view = viewFactory.createView();
        Client client = new Client("localhost", 1234);
        UIController ui = new UIController(view, client);
        client.attach(ui);
    }
}
