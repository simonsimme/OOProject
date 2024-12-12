package Main;

import Model.Server.Server;
public class ServerApplication {
    public static void main(String[] args) {
            Server server = Server.createServerInstance(1234);
            server.startListening();


        // Wait a moment to ensure the server is up and running
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
