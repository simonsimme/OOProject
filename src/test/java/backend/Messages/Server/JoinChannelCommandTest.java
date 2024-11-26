package backend.Messages.Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link JoinChannelCommand} class
 * Tests the functionality of the methods in the {@code JoinChannelCommand} class
 */
class JoinChannelCommandTest {
    private String userName;
    private String channelName;
    private String password;
    private JoinChannelCommand jcc;

    /**
     * Sets up the test data and initializes a {@code JoinChannelCommand} instance.
     */
    @BeforeEach
    void setUp() {
        this.userName = "userName";
        this.channelName = "channelName";
        this.password = "passWord";
        this.jcc = new JoinChannelCommand(userName, channelName, password);
    }

    /**
     * Test the {@code getChannelName} method to ensure it returns the correct channelName.
     */
    @Test
    void getChannelName() {
        String result = jcc.getChannelName();
        assertEquals(channelName, result);
    }
    /**
     * Test the {@code getUserName} method to ensure it returns the correct username.
     */
    @Test
    void getUserName() {
        String result = jcc.getUserName();
        assertEquals(userName, result);
    }
    /**
     * Test the {@code getPassword} method to ensure it returns the correct password.
     */
    @Test
    void getPassword() {
        String result = jcc.getPassword();
        assertEquals(password, result);
    }
    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code ServerMessageVisitor}.
     */
    @Test
    void accept() {
        // Arrange: Create a mock ServerMessageVisitor
        ServerMessageVisitor mockVisitor = mock(ServerMessageVisitor.class);
        // Act: Call accept on the command with the mock visitor
        jcc.accept(mockVisitor);
        // Assert: Verify the handle method is called with the correct command
        verify(mockVisitor, times(1)).handle(jcc);
    }
}