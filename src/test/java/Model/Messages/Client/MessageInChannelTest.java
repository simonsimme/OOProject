package Model.Messages.Client;

import Model.Client.ClientVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link MessageInChannel} class
 * Tests the functionality of the methods in the {@code MessageInChannel} class
 */
class MessageInChannelTest {
    private String userName;
    private String channelName;
    private String message;
    private MessageInChannel msgInChannel;

    /**
     * Sets up the test data and initializes a {@code CreateChannelResponse} instance.
     */
    @BeforeEach
    void setUp() {
        this.userName = "Username";
        this.channelName = "channelName";
        this.message = "message to be tested";
        this.msgInChannel = new MessageInChannel(userName, channelName, message, false);
    }

    /**
     * Test the {@code getChannelName} method to ensure it returns the correct channelName.
     */
    @Test
    void getChannelName() {
        String result = msgInChannel.getChannelName();
        assertEquals(channelName, result);
    }
    /**
     * Test the {@code getMessage} method to ensure it returns the correct channelName.
     */
    @Test
    void getMessage() {
        String result = msgInChannel.getMessage();
        assertEquals(message, result);
    }

    /**
     * Test the {@code getUserName} method to ensure it returns the correct channelName.
     */
    @Test
    void getUserName() {
        String result = msgInChannel.getUserName();
        assertEquals(userName, result);
    }
    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code ClientVisitor}.
     */
    @Test
    void accept() {
        //Arrange: Create a mock ClientVisitor
        ClientVisitor mockVisitor = mock(ClientVisitor.class);
        //Act: Call accept on the response with the mock Visitor
        msgInChannel.accept(mockVisitor);
        //Assert: Verify the handle method is called with the correct response
        verify(mockVisitor, times(1)).handle(msgInChannel);
    }

    /**
     * Test the {@code getMessageAsString} method to ensure it returns the correct channelName.
     */
    @Test
    void getMessageAsString() {
        String result = msgInChannel.getMessageAsString();
        assertEquals(message, result);
    }

    /**
     * Test the {@code getSenderAsString} method to ensure it returns the correct channelName.
     */
    @Test
    void getSenderAsString() {
        String result = msgInChannel.getSenderAsString();
        assertEquals(userName, result);
    }
}