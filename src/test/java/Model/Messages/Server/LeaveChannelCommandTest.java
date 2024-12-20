package Model.Messages.Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link LeaveChannelCommand} class
 * Tests the functionality of the methods in the {@code LeaveChannelCommand} class
 */
class LeaveChannelCommandTest {
    private String userName;
    private String channelName;
    private LeaveChannelCommand lcc;
    /**
     * Sets up the test data and initializes a {@code LeaveChannelCommand} instance.
     */
    @BeforeEach
    void setUp() {
        this.userName = "userName";
        this.channelName = "channelName";
        this.lcc = new LeaveChannelCommand(userName, channelName);
    }

    @Test
    void getChannelName() {
        String result = lcc.getChannelName();
        assertEquals(channelName, result);
    }
    /**
     * Test the {@code getUserName} method to ensure it returns the correct username.
     */
    @Test
    void getUserName() {
        String result = lcc.getUserName();
        assertEquals(userName, result);
    }
    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code ServerMessageVisitor}.
     */
    @Test
    void accept() {
        // Arrange: Create a mock ServerMessageVisitor
        ServerMessageVisitor mockVisitor = mock(ServerMessageVisitor.class);
        // Act: Call accept on the command with the mock visitor
        lcc.accept(mockVisitor);
        // Assert: Verify the handle method is called with the correct command
        verify(mockVisitor, times(1)).handle(lcc);
    }
}