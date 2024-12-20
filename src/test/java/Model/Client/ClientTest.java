package Model.Client;

import Model.Messages.Server.SendMessageInChannelCommand;
import Model.Messages.UI.UIMessage;
import Model.Server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@code Client} class.
 */
public class ClientTest {

    private Client client;
    private Server server;
    private Thread serverThread;

    /**
     * Sets up a test environment by starting a mock server and initializing the client.
     *
     * @throws InterruptedException if interrupted while waiting for the server to start.
     */
    @BeforeEach
    void setUp() throws InterruptedException {

        server = Server.createServerInstance(8080);
        serverThread = new Thread(server::startListening);
        serverThread.start();

        // Wait for the server to start
        Thread.sleep(50);

        client = new Client("localhost", 8080);
    }

    /**
     * Tears down the test environment by stopping the server and cleaning up resources.
     *
     * @throws IOException          if an I/O error occurs while stopping the server.
     * @throws InterruptedException if interrupted while waiting for the server thread to finish.
     */
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
     * Tests the {@code setNickName} method to ensure the client's username is updated correctly.
     */
    @Test
    void setNickName() {
        String username = "Test username";
        client.setNickName(username);
        assertEquals(username, client.getUserName());
    }

    /**
     * Test the {@code getUserName} method to ensure it returns the correct username.
     */
    @Test
    void getUserName() {
        String defaultUser = "client default user name";
        assertEquals(defaultUser, client.getUserName());
    }
    /**
     * Test the {@code getCurrentChannelName} method to ensure it returns the correct channel name.
     */
    @Test
    void getCurrentChannelName() throws InterruptedException {
        String channelName = "Testchannel";
        server.createChannel(channelName, "Password");
        client.joinChannel(channelName, "Password");
        Thread.sleep(50);
        String result = client.getCurrentChannelName();
        assertEquals(channelName, result);
    }
    /**
     * Test the {@code joinChannel} client's ability to join a channel
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
     * Test the {@code leaveChannel} client's ability to leave a channel
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
        client.leaveChannel();

        // check the client is not in the channel
        assertEquals("empty-channel", client.getCurrentChannelName());
    }


    /**
     * Test the client's ability to switch channels with provided channel name
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
     * Test the client's ability to switch channels to next in list
     * */
    @Test
    void switchChannelToNext() throws InterruptedException {
        String channelName1 = "testChannel1";
        String channelName2 = "testChannel2";
        String password = "password";


        server.createChannel(channelName1, password);
        server.createChannel(channelName2, password);

        client.joinChannel(channelName1, password);
        client.joinChannel(channelName2, password);
        Thread.sleep(50);
        client.switchChannel();

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
     * Tests the {@code sendMessage} method using Mockito to verify server communication.
     */
    @Test
    void sendMessage() {
        // Create a spy for the ClientCommunicationManager
        ClientCommunicationManager client = spy(new ClientCommunicationManager("localhost", 8080, null, null));

        // Test data
        String userName = "testUser";
        String channel = "testChannel";
        String message = "Hello, world!";
        boolean isServerMessage = false;

        // Call the method under test
        client.sendMessage(userName, channel, message, isServerMessage);

        // Verify that sendMessageToServer was called with the correct ServerMessage
        verify(client).sendMessageToServer(argThat(argument -> {
            if (argument instanceof SendMessageInChannelCommand command) {
                return command.getUserName().equals(userName) &&
                        command.getChannelName().equals(channel) &&
                        command.getMessage().equals(message) &&
                        !command.isServerMessage(); // Ensure isServerMessage is false
            }
            return false;
        }));
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

    /**
     * Tests the {@code attach} method to ensure that a {@code ClientObserver} is correctly attached
     * and receives notifications when the client notifies observers.
     */
    @Test
    void attachObserverTest() {
        // Create a mock observer
        ClientObserver observer = mock(ClientObserver.class);

        // Attach the observer
        client.attach(observer);

        // Notify observers with a mock message
        UIMessage message = mock(UIMessage.class);
        client.notifyObservers(message);

        // Verify the observer received the notification
        verify(observer, times(1)).update(message);
    }

    /**
     * Tests the {@code detach} method to ensure that a {@code ClientObserver} is correctly detached
     * and does not receive notifications after being removed.
     */
    @Test
    void detachObserverTest() {
        // Create a mock observer
        ClientObserver observer = mock(ClientObserver.class);

        // Attach and then detach the observer
        client.attach(observer);
        client.detach(observer);

        // Notify observers with a mock message
        UIMessage message = mock(UIMessage.class);
        client.notifyObservers(message);

        // Verify the observer did not receive the notification
        verify(observer, times(0)).update(message);
    }

    /**
     * Tests the {@code notifyObservers} method to ensure that all attached observers receive notifications
     * when the client sends an update.
     */
    @Test
    void notifyObserversTest() {
        ClientObserver observer1 = mock(ClientObserver.class);
        ClientObserver observer2 = mock(ClientObserver.class);

        client.attach(observer1);
        client.attach(observer2);

        UIMessage message = mock(UIMessage.class);

        client.notifyObservers(message);

        // Verify that both observers received the message
        verify(observer1, times(1)).update(message);
        verify(observer2, times(1)).update(message);
    }
}
