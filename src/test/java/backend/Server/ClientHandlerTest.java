package backend.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * This class tests the functionality of the {@code ClientHandler} class,
 * focusing on client interactions with chat channels such as joining and leaving channels.
 */
public class ClientHandlerTest {
    private Server Server;
    private ClientHandler clientHandler;
    /**
     * Sets up the test environment by creating a {@code Server} instance and
     * initializing a {@code ClientHandler} with a test socket before each test case.
     */
    @BeforeEach
    void setUp() {
        //setup the server and start listening for the clients
        Server = Server.createServerInstance(8080);
        clientHandler = new ClientHandler(new Socket(), Server);

    }
    /**
     * Tests the {@code joinChannel} method of {@code ClientHandler}.
     * Verifies that:
     * <ul>
     *     <li>The client successfully joins an existing channel when the correct password is provided.</li>
     *     <li>The client is added to the channel's list of clients.</li>
     * </ul>
     */
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

    /**
     * Test the {@code leaveChannel} method of {@code ClientHandler}
     * verifies that
     * <li>The client successfully leaves a previously channel</li>
     * <li>The client is removed from the channel list of clients</li>
     */
    @Test
    void testLeaveChannelSuccess() {
        String channel = "testChannel";
        String password = "password";
        Server.createChannel(channel, password);
        clientHandler.joinChannel(channel, password);

        clientHandler.leaveChannel();
        ChatChannel channels = Server.getChannel(channel);

        assertFalse(channels.getClients().contains(clientHandler));
    }
}