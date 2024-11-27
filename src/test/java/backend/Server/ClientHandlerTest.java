package backend.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientHandlerTest {
    private Server Server;
    private ClientHandler clientHandler;

    @BeforeEach
    void setUp() {
        //setup the server and start listening for the clients
        Server = Server.createServerInstance(8080);
        clientHandler = new ClientHandler(new Socket(), Server);

    }
    @Test
    void testJoinChannelSuccess() {
        Server.createChannel("testChannel", "password123"); // Creating a channel on the server
        // Assuming the password is correct
        clientHandler.joinChannel("testChannel", "password123");

        // Retrieve the channel from the server to verify
        ChatChannel channel = Server.getChannel("testChannel");

        // Verify that the client has been added to the correct channel
        assertTrue(channel.getClients().contains(clientHandler), "Client should be in the channel.");
    }
}