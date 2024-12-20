package Model.Messages.Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link SendMessageInChannelCommand} class
 * Tests the functionality of the methods in the {@code SendMessageInChannelCommand} class
 */

class SendMessageInChannelCommandTest {
    private String userName;
    private String channelName;
    private String message;
    private SendMessageInChannelCommand smicc;

    /**
     * Sets up test data and initializes a {@code SendMessageInChannelCommand} instance
     */
    @BeforeEach
    void setUp() {
        this.userName = "userName";
        this.channelName = "channelName";
        this.message = "message";
        smicc = new SendMessageInChannelCommand(userName, channelName, message, false);
    }

    /**
     * Tests the {@code getMessage} method to ensure it returns the correct message.
     */
    @Test
    void getMessageTest() {
        String result = smicc.getMessage();
        assertEquals(message, result);
    }
    /**
     * Tests the {@code getUserName} method to ensure it returns the correct userName.
     */
    @Test
    void getUserNameTest() {
        String result = smicc.getUserName();
        assertEquals(userName, result);
    }
    /**
     * Tests the {@code getChannel} method to ensure it returns the correct channelName.
     */
    @Test
    void getChannelName() {
        String result = smicc.getChannelName();
        assertEquals(channelName, result);
    }
    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code ServerMessageVisitor}.
     */
    @Test
    void testAcceptCallsHandleOnVisitor() {
        // Arrange: Create a mock ServerMessageVisitor
        ServerMessageVisitor mockVisitor = mock(ServerMessageVisitor.class);

        // Act: Call accept on the command with the mock visitor
        smicc.accept(mockVisitor);

        // Assert: Verify the handle method is called with the correct command
        verify(mockVisitor, times(1)).handle(smicc);
    }
}