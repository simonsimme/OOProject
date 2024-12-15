package Model.Client;

import Model.Server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClientTest {

    private Client client;
    private Server server;
    private Thread serverThread;

    @BeforeEach
    void setUp() throws InterruptedException {

        server = Server.createServerInstance(8080);
        serverThread = new Thread(server::startListening);
        serverThread.start();

        // Wait for the server to start
        Thread.sleep(50);

        client = new Client("localhost", 8080);
    }

    @AfterEach
    void tearDown() throws IOException, InterruptedException {

        if (server != null) {
            server.stop();
        }

        // Wait for the server thread to finish
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.join();
        }

        client = null;
        server = null;
        serverThread = null;
    }

    /**
     * Test the client's ability to join a channel
     * */
    @Test
    void joinChannelTest() throws InterruptedException {
        String channelName = "testChannel";
        String password = "password";

        client.createChannel(channelName, password);
        client.joinChannel(channelName, password);

        Thread.sleep(50);
        // check the client has joined the channel
        assertEquals(channelName, client.getCurrentChannelName());
    }
    /**
     * Ensures that the client cannot join a channel with the wrong password
     * */
    @Test
    void joinChannelWrongPasswordTest() throws InterruptedException {
        String channelName = "testChannel";
        String password = "password";
        String wrongPassword = "wrongPassword";

        client.createChannel(channelName, password);
        client.leaveChannel(channelName);
        client.joinChannel(channelName, wrongPassword);

        Thread.sleep(50);
        // check the client has not joined the channel
        assertEquals("empty-channel", client.getCurrentChannelName());
    }

    /**
     * Test the client's ability to leave a channel
     * */
    @Test
    void leaveChannelTest() {
        String channelName = "testChannel";
        String password = "password";

        server.createChannel(channelName, password);
        client.joinChannel(channelName, password);


        client.leaveChannel(channelName);

        // check the client is no longer in the channel
        assertEquals("empty-channel", client.getCurrentChannelName());
    }

    /**
     * Ensures the client cant leave a channel they are not in
     * No action/exception should be thrown
     * */
    @Test
    void leaveChannelNotInChannelTest() {
        String channelName = "testChannel";
        String password = "password";

        client.createChannel(channelName, password);
        client.leaveChannel(channelName);

        // check the client is not in the channel
        assertEquals("empty-channel", client.getCurrentChannelName());
    }


    /**
     * Test the client's ability to switch channels
     * */
    @Test
    void switchChannelTest() throws InterruptedException {
        String channelName1 = "testChannel1";
        String channelName2 = "testChannel2";
        String password = "password";


        server.createChannel(channelName1, password);
        server.createChannel(channelName2, password);

        client.joinChannel(channelName1, password);
        client.joinChannel(channelName2, password);
        Thread.sleep(50);
        client.switchChannel(channelName1);

        // check the current channel is updated
        assertEquals(channelName1, client.getCurrentChannelName());
    }

    /**
     * Ensures the client cannot switch to a channel they are not in
     * */
    @Test
    void switchChannelNotInChannelTest() {
        String channelName = "testChannel";
        String password = "password";

        client.createChannel(channelName, password);
        client.leaveChannel(channelName);
        client.switchChannel(channelName);

        // check the client is not in the channel
        assertEquals("empty-channel", client.getCurrentChannelName());
    }

    /**
     * Tests the createChannel method of the client
     * */
    @Test
    void createChannelTest() throws InterruptedException {
        String channelName = "testChannel";
        String password = "password";

        client.createChannel(channelName, password);

        Thread.sleep(50);
        // check the channel was created
        assertEquals(channelName, client.getCurrentChannelName());
    }

    /**
     * Ensures the client cannot create a channel with the same name as an existing channel
     * */
    @Test
    void createChannelDuplicateNameTest() throws InterruptedException {
        String channelName = "testChannel";
        String password = "password";

        client.createChannel(channelName, password);
        client.createChannel(channelName, password);

        Thread.sleep(50);
        // check the channel was not created
        assertEquals(1, client.getChannelNames().size());
    }

    /**
     * Testing client during stress, 50 create/join/leave requests gets handled to check for bugs, None found
     * */
    @Test
    void stressTest() throws InterruptedException {
        int amountOfChannels = 50;
        String channelName = "testChannel";
        String password = "password";

        for (int i = 0; i < amountOfChannels; i++) {
            client.createChannel(channelName + i, password);
            client.joinChannel(channelName + i, password);
        }
        Thread.sleep(50);
        // check the client is in the last channel
        assertEquals(channelName + 49, client.getCurrentChannelName());

        for(int i = 0; i < amountOfChannels; i++) {
            client.leaveChannel(channelName + i);
        }

        Thread.sleep(50);
        // check the client is in the empty channel
        assertEquals("empty-channel", client.getCurrentChannelName());
    }




}
