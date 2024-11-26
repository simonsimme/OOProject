package backend.Server;
import org.junit.jupiter.api.BeforeEach;

public class ClientHandlerTest {
    /*
    we will introdue a test for the ClientHandler class.... this will insure that the connectection is established and the input and output streams are set up correctly
    that we recieve to the server are handled in a correct manner...
     */

    //create a server instance and in addition to a client handler instance
    Server server;
    ClientHandler clientHandler;

    @BeforeEach
    public void setUp() {
        //setup the server and start listening for the clients
        server = Server.createServerInstance(8080);
        server.startListening();
    }
}
