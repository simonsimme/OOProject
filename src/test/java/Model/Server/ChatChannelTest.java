package Model.Server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ChatChannelTest {
    protected ChatChannel channel;
    protected String name;
    protected String password;
    protected ClientHandler clientHandler;
    protected Server server;

    @BeforeEach
    void setUp() {
        int port = 12345;
        this.server = Server.createServerInstance(port);
        this.name = "name";
        this.password = "password";
        this.channel = new ChatChannel(name, password);
        this.clientHandler = new ClientHandler(new Socket(), server);
    }
    @AfterEach
    void tearDown() throws IOException {
        channel = null;
        server.stop();
    }
    @Test
    void validatePassword() {
        String wrong = "wrong";
        assertFalse(channel.validatePassword(wrong));
        assertTrue(channel.validatePassword(password));
    }
    @Test
    void removeClient() {
        channel.addClient(clientHandler);
        assertTrue(channel.getClients().contains(clientHandler));

        channel.removeClient(clientHandler);

        assertFalse(channel.getClients().contains(clientHandler));
    }
    @Test
    void getClients() {
        channel.addClient(clientHandler);
        assertTrue(channel.getClients().contains(clientHandler));
    }

    @Test
    void getName() {
        assertEquals(name, channel.getName(), "Channel name should be " + name);
    }
}