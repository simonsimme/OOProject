package backend.Server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class test the functionality of the {@code Server} class.
 * It verifies the singleton behaviour of the server instance and
 * ensures proper management of chat channels
 */
class ServerTest {
    //Server instance used for testing.
    private Server server;
    //The port number used for testing.
    private int port;
    private Socket testClientSocket;

    /**
     * Sets up the test environment by initializing the server instance
     * and assigning a test port before each test case.
     */
    @BeforeEach
    void setUp() {
        port = 12345;
        server = Server.createServerInstance(port);

        Thread serverThread = new Thread(() -> server.startListening());
        serverThread.setDaemon(true);
        serverThread.start();
    }

    /**
     * Cleans up after each test case.
     */
    @AfterEach
    void tearDown() throws IOException {
        // Clean up: Close any resources
        if (testClientSocket != null && !testClientSocket.isClosed()) {
            testClientSocket.close();
        }
        server.stop();
    }
    @Test
    void testStopServer() throws IOException, InterruptedException {
        // Create a client to simulate a connection
        testClientSocket = new Socket("localhost", 12345);

        // The server should accept this connection and create a handler
        assertTrue(testClientSocket.isConnected(), "Client should be connected");

        // Now, stop the server
        server.stop();
        Thread.sleep(500);

        // Try to create another client connection after the server has been stopped
        Socket anotherClientSocket = null;
        try {
            anotherClientSocket = new Socket("localhost", 12345);
            fail("Server should not accept new connections after stop() is called");
        } catch (IOException e) {
            // Expected: since the server is stopped, this should throw an IOException
            assertTrue(e.getMessage().contains("Connection refused"), "Expected 'Connection refused' exception");
        } finally {
            if (anotherClientSocket != null && !anotherClientSocket.isClosed()) {
                anotherClientSocket.close();
            }
        }
    }

    /**
     * Test {@code createServerInstance} method to verify that:
     * The server instance is successfully created.
     * The singleton pattern is maintained
     */
    @Test
    void createServerInstance() {
        //Check that server is not Null
        assertNotNull(server);

        //Attempt to create a second instance
        Server server2 = Server.createServerInstance(port);
        assertNotNull(server2);
        assertSame(server, server2);
    }

    /**
     * Tests the {@code getOrCreateChannel} method of the {@code Server} class
     * for a new channel. Verifies that:
     * <ul>
     *     <li>A new {@code ChatChannel} is created when the channel name does not exist</li>
     *     <li>The created channel is stored in the server</li>
     *     <li>The channel password is correctly validated</li>
     * </ul>
     */
    @Test
    public void testGetOrCreateChannel_NewChannel() {
        String channelName = "newChannel";
        String password = "secret";

        ChatChannel channel = server.getOrCreateChannel(channelName, password);

        // Ensure the channel is created and validates the password
        assertNotNull(channel, "The channel should not be null");
        assertTrue(channel.validatePassword(password), "The channel password should match the input password");

        // Confirm that the channel is stored in the server
        ChatChannel retrievedChannel = server.getChannel(channelName);
        assertSame(channel, retrievedChannel, "The created channel should be retrievable from the server");
    }
    /**
     * Tests the {@code getOrCreateChannel} method of the {@code Server} class
     * for an existing channel. Verifies that:
     * <ul>
     *     <li>The same {@code ChatChannel} instance is returned for an existing name</li>
     *     <li>The original password remains valid</li>
     *     <li>No new channel is created for the same name</li>
     * </ul>
     */
    @Test
    public void testGetOrCreateChannel_ExistingChannel() {
        String channelName = "existingChannel";
        String password = "password";

        // Create a channel first
        server.createChannel(channelName, password);

        // Attempt to get or create the channel with the same name
        ChatChannel channel = server.getOrCreateChannel(channelName, "wrongPassword");

        // Ensure the same channel is returned (not a new one)
        assertNotNull(channel, "The channel should not be null");
        assertTrue(channel.validatePassword(password), "The password should match the original password");

        // Confirm that the channel from `getOrCreateChannel` matches the stored one
        ChatChannel retrievedChannel = server.getChannel(channelName);
        assertSame(channel, retrievedChannel, "The retrieved channel should match the one created earlier");
    }
}