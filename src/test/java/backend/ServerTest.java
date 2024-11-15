package backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    private Server server;
    private int port;
    @BeforeEach
    void setUp() {
        port = 1234;
        server = Server.createServerInstance(port);
    }

    @AfterEach
    void tearDown() {
    }

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