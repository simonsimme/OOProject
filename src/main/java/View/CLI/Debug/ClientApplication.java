package View.CLI.Debug;

import View.CLI.CLIController;
import View.CLI.CLIView;
import Model.Client.Client;

/**
 * Run this to start a client
 */
public class ClientApplication {

    private static final String ip = "localhost";
    private static final int port = 2461;

    public static void main(String[] args){
        Client client = new Client(ip,port);
        CLIView view = new CLIView(System.out);
        client.attach(view);
        CLIController controller = new CLIController(System.in,view,client);
        controller.run();
        System.out.println(
                "Client started successfully!\n" +
                        "Successful connection to server on ...\n" +
                        "ip :" + ip + "\n" +
                        "port : " + port + "\n" +
                        "\n" +
                        "Type /help for a list of commands"
        );
    }

}
