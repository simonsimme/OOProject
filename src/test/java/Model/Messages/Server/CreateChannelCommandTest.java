package Model.Messages.Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit test for {@link CreateChannelCommand} class
 * Tests the functionality of the methods in the {@code CreateChannelCommand} class
 */
public class CreateChannelCommandTest {
    CreateChannelCommand command;

    @BeforeEach
    void setCreateChannelCommand (){
        command = new CreateChannelCommand("user1", "channel1");
    }

    @Test
    void testDefaultConstructor() {
        assertEquals("user1", command.getUserName());
        assertEquals("channel1", command.getChannelName());
        assertEquals("", command.getChannelPassword());
    }

    @Test
    void testParameterizedConstructor() {
        command = new CreateChannelCommand("user1", "channel1", "password123");
        assertEquals("user1", command.getUserName());
        assertEquals("channel1", command.getChannelName());
        assertEquals("password123", command.getChannelPassword());
    }
    @Test
    void testParameterizedConstructor2() {
        //Breake the test
        assertEquals("user1", command.getUserName());
        assertEquals("channel1", command.getChannelName());
        //this should fail beacuse the password object is not initialized
        assertNotEquals("password123", command.getChannelPassword());
    }
    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code ServerMessageVisitor}.
     */
    @Test
    void testAcceptCallsHandleOnVisitor() {
        //Create a mock ServerMessageVisitor
        ServerMessageVisitor mockVisitor = mock(ServerMessageVisitor.class);
        //Call accept on the command with the mock visitor
        command.accept(mockVisitor);
        //Verify the handle method is called with the correct command
        verify(mockVisitor, times(1)).handle(command);
    }
}
