package Model.Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ClientChannel} class.
 * These tests validate the functionality of the ClientChannel methods
 * including message recording, history management, and channel name handling.
 */
class ClientChannelTest {
    private ClientChannel clientChannel;
    private String channelName;

    /**
     * Initializes the {@link ClientChannel} instance before each test.
     */
    @BeforeEach
    void setUp() {
        channelName = "ChannelName";
        clientChannel = new ClientChannel(channelName);
    }

    /**
     * Tests the {@link ClientChannel#recordMessage(String)} method.
     * Ensures that messages are properly appended to the channel's history.
     */
    @Test
    void recordMessage() {
        String testString = "Test History String";
        clientChannel.setHistory(testString);
        clientChannel.recordMessage(testString);
        assertEquals(testString+testString, clientChannel.getHistory().toString());

    }

    /**
     * Tests the {@link ClientChannel#getHistory()} method.
     * Ensures that the correct history is retrieved after setting a history string.
     */
    @Test
    void getHistory() {
        String testString = "Test History String";
        clientChannel.setHistory(testString);
        assertEquals(testString, clientChannel.getHistory().toString());

    }

    /**
     * Tests the {@link ClientChannel#getName()} method.
     * Ensures that the channel's name is correctly returned.
     */
    @Test
    void getName() {
        assertEquals(channelName, clientChannel.getName());
    }

    /**
     * Tests the {@link ClientChannel#setHistory(String)} method.
     * Ensures that the channel's history is set correctly.
     */
    @Test
    void setHistory() {
        String testString = "Test History String";
        clientChannel.setHistory(testString);
        assertEquals(testString, clientChannel.getHistory().toString());
    }

    /**
     * Tests the {@link ClientChannel#setName(String)} method.
     * Ensures that the channel's name is updated correctly.
     */
    @Test
    void setName() {
        String newChannelName = "NewChannel";
        clientChannel.setName(newChannelName);
        assertEquals(newChannelName, clientChannel.getName());
    }
}