package backend.toLater;

import backend.Server;
import backend.toLater.Client;

public class ServerMain {

    static Server server;
    static Client client;

    public static void main(String[]arg){
        server = Server.createServerInstance(8080);
        client = new Client("127.0.0.1", 8080);
    }
}
