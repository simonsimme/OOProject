package Model.Messages.Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link RetrieveChatHistoryCommand} class
 * Tests the functionality of the methods in the {@code CreateChannelCommand} class
 */
public class RetrieveChatHistoryCommandTest {
    private RetrieveChatHistoryCommand command;
    private String channelName;
    private String userName;

    /**
     * Sets up the test data and initializes a {@code RetrieveChatHistoryCommand} instance.
     */
    @BeforeEach
    void setUp() {
        this.userName = "userName";
        this.channelName = "channelName";
        this.command = new RetrieveChatHistoryCommand(userName, channelName);
    }
    /**
     * Test the {@code getChannelName} method to ensure it returns the correct channelName.
     */
    @Test
    void getChannelName() {
        String result = command.getChannelName();
        assertEquals(channelName, result);
    }
    @Test
    void accept() {
        // Arrange: Create a mock ServerMessageVisitor
        ServerMessageVisitor mockVisitor = mock(ServerMessageVisitor.class);
        // Act: Call accept on the command with the mock visitor
        command.accept(mockVisitor);
        // Assert: Verify the handle method is called with the correct command
        verify(mockVisitor, times(1)).handle(command);
    }
}
