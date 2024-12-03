package Model.Server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests the functionality of the {@code ClientHandler} class,
 * focusing on client interactions with chat channels such as joining and leaving channels.
 */
public class ClientHandlerTest {
    private Server Server;
    private ClientHandler clientHandler;

    private ClientHandler clientHandler2;
    private String channelName;
    private String password;
    /**
     * Sets up the test environment by creating a {@code Server} instance and
     * initializing a {@code ClientHandler} with a test socket before each test case.
     */
    @BeforeEach
    void setUp() {
        //Set up the server and start listening for the clients
        Server = Server.createServerInstance(8080);
        clientHandler = new ClientHandler(new Socket(), Server);
        clientHandler2 = new ClientHandler(new Socket(), Server);
        channelName = "testChannel";
        password = "password";
    }
    @AfterEach
    void tearDown() {
        try {
            Server.stop();
        } catch (IOException e) {
            System.out.println("Error in stopping the server" + e.getMessage());
        }
        Server = null;
        clientHandler = null;
        channelName = null;
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
        clientHandler.createChannel(channelName, password); // Creating a channel on the server
        // Assuming the password is correct
        clientHandler.joinChannel(channelName, password);

        // Retrieve the channel from the server to verify
        ChatChannel channel = Server.getChannel(channelName);

        // Verify that the client has been added to the correct channel
        assertTrue(channel.getClients().contains(clientHandler), "Client should be in the channel.");
    }
    @Test
    void testJoinChannelWithTwoHandlers() {
        clientHandler.createChannel(channelName, password); // Creating a channel on the server
        // Assuming the password is correct
        clientHandler.joinChannel(channelName, password);
        //the second client joins the channel
        clientHandler2.joinChannel(channelName, password);

        // Retrieve the channel from the server to verify
        ChatChannel channel = Server.getChannel(channelName);

        // Verify that the client has been added to the correct channel
        assertTrue(channel.getClients().contains(clientHandler), "Client should be in the channel.");
        assertTrue(channel.getClients().contains(clientHandler2), "Client should be in the channel.");
    }

    /**
     * Test the {@code leaveChannel} method of {@code ClientHandler}
     * verifies that
     * <li>The client successfully leaves a previously channel</li>
     * <li>The client is removed from the channel list of clients</li>
     */
    @Test
    void testLeaveChannelSuccess() {
        Server.createChannel(channelName, password);
        clientHandler.joinChannel(channelName, password);

        clientHandler.leaveChannel(channelName);
        ChatChannel channels = Server.getChannel(channelName);

        assertFalse(channels.getClients().contains(clientHandler));
    }
    /**
     * Test {@code getCurrentChannel} method of {code ClientHandler}.
     * Verifies that:
     * <li>The client correctly identifies the current channel after joining it</li>
     * <li>The current channel returned by method matches the channel instance retried from the server</li>
     */
    @Test
    void testGetCurrentChannel() {
        Server.createChannel(channelName, password);
        ChatChannel chatChannel = Server.getChannel(channelName);
        clientHandler.joinChannel(channelName, password);
        assertEquals(chatChannel ,clientHandler.getCurrentChannel());
    }
    /**
     * Tests the {@code createChannel} method of {@code ClientHandler}.
     * Verifies that:
     * <ul>
     *     <li>A new channel is successfully created on the server with the specified name and password.</li>
     *     <li>The client is automatically added to the newly created channel.</li>
     *     <li>The client's current channel is updated to the newly created channel.</li>
     * </ul>
     */
    @Test
    void testCreateChannel() {
        clientHandler.createChannel(channelName, password);
        ChatChannel chatChannel = Server.getChannel(channelName);

        //Assert that the channel has been created in the server
        assertEquals(chatChannel, clientHandler.getCurrentChannel());
        //Assert that the channel has been added to ChatChannel
        assertTrue(chatChannel.getClients().contains(clientHandler));
    }
}