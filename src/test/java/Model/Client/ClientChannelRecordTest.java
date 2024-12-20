package Model.Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ClientChannelRecord} class.
 * Tests the functionality of managing channels, switching channels,
 * recording messages, and adding/removing channels.
 */
class ClientChannelRecordTest {
    private ClientChannelRecord clientChannelRecord;
    private String channelName1;
    private String channelName2;

    /**
     * Sets up the test environment by initializing a {@link ClientChannelRecord}
     * and adding two channels before each test.
     */
    @BeforeEach
    void setUp() {
        clientChannelRecord = new ClientChannelRecord();
        channelName1 = "testChannel1";
        channelName2 = "testChannel2";
        clientChannelRecord.addNewChannel(channelName1);
        clientChannelRecord.addNewChannel(channelName2);
    }

    /**
     * Tests the switching of channels by name.
     * Verifies that the current channel changes to the specified channel.
     */
    @Test
    void switchToChannel() {
        clientChannelRecord.switchToChannel(channelName1);
        assertEquals(channelName1, clientChannelRecord.getCurrentChannelName());

        clientChannelRecord.switchToChannel(channelName2);
        assertEquals(channelName2, clientChannelRecord.getCurrentChannelName());
    }

    /**
     * Tests the switching of channels to the next channel in the list.
     * Verifies the circular behavior when switching from the last channel to the first.
     */
    @Test
    void switchToNextChannel() {
        clientChannelRecord.switchToChannel(channelName1);
        assertEquals(channelName1, clientChannelRecord.getCurrentChannelName());

        String nextChannel = clientChannelRecord.switchToNextChannel();
        assertEquals(channelName2, nextChannel);
        assertEquals(channelName2, clientChannelRecord.getCurrentChannelName());

        // Test the circular behavior
        String firstChannel = clientChannelRecord.switchToNextChannel();
        assertEquals(channelName1, firstChannel);
        assertEquals(channelName1, clientChannelRecord.getCurrentChannelName());
    }


    /**
     * Tests adding a new channel.
     * Verifies that the new channel is added to the list and set as the current channel.
     */
    @Test
    void addNewChannel() {
        String newChannelName = "newChannel";
        clientChannelRecord.addNewChannel(newChannelName);
        assertTrue(clientChannelRecord.getChannelNames().contains(newChannelName));
        assertEquals(newChannelName, clientChannelRecord.getCurrentChannelName());
    }

    /**
     * Tests removing an existing channel.
     * Verifies that the channel is removed from the list and that the next channel is selected as current.
     */
    @Test
    void removeChannel() {
        clientChannelRecord.switchToChannel(channelName1);
        clientChannelRecord.removeChannel(channelName1);

        assertFalse(clientChannelRecord.getChannelNames().contains(channelName1));
        assertEquals(channelName2, clientChannelRecord.getCurrentChannelName());
    }

    /**
     * Tests getting the name of the current channel.
     * Verifies that the correct channel name is returned when queried.
     */
    @Test
    void getCurrentChannelName() {
        clientChannelRecord.switchToChannel(channelName1);
        assertEquals(channelName1, clientChannelRecord.getCurrentChannelName());

        clientChannelRecord.switchToChannel(channelName2);
        assertEquals(channelName2, clientChannelRecord.getCurrentChannelName());
    }

    /**
     * Tests getting the names of all channels.
     * Verifies that the list contains the names of all channels added to the record.
     */
    @Test
    void getChannelNames() {
        assertTrue(clientChannelRecord.getChannelNames().contains(channelName1));
        assertTrue(clientChannelRecord.getChannelNames().contains(channelName2));
    }
}