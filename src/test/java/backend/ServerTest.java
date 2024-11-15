package backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    /**
     * Sets up the test environment by initializing the server instance
     * and assigning a test port before each test case.
     */
    @BeforeEach
    void setUp() {
        port = 1234;
        server = Server.createServerInstance(port);
    }

    /**
     * Cleans up after each test case.
     */
    @AfterEach
    void tearDown() {
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

    @Test
    void getOrCreateChannel() {
    }
}