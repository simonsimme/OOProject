package Model.Messages.UI;

import View.components.Decoraters.HandleMessageDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link DisplayMessage} class
 * Tests the functionality of the methods in the {@code DisplayMessage} class
 */
class DisplayMessageTest {
    private String userName;
    private String message;
    private String channelName;
    private DisplayMessage displayMessage;

    /**
     * Sets up the test data and initializes a {@code DisplayError} instance.
     */
    @BeforeEach
    void setUp() {
        this.userName = "UserName";
        this.message = "Test message";
        this.channelName = "channelName";
        this.displayMessage = new DisplayMessage(userName, message, channelName);

    }

    /**
     * Test the {@code getChannelName} method to ensure it returns the correct channel name.
     */
    @Test
    void getChannelName() {
        String result = displayMessage.getChannelName();
        assertEquals(channelName, result);
    }

    /**
     * Test the {@code getUserName} method to ensure it returns the correct username.
     */
    @Test
    void getUserName() {
        String result = displayMessage.getUserName();
        assertEquals(userName, result);
    }

    /**
     * Test the {@code getMessage} method to ensure it returns the correct message.
     */
    @Test
    void getMessage() {
        String result = displayMessage.getMessage();
        assertEquals(message, result);
    }

    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code HandleMessageDecorator}.
     */
    @Test
    void accept() {
        //Arrange: Create a mock ClientVisitor
        HandleMessageDecorator mockVisitor = mock(HandleMessageDecorator.class);
        //Act: Call accept on the response with the mock Visitor
        displayMessage.accept(mockVisitor);
        //Assert: Verify the handle method is called with the correct response
        verify(mockVisitor, times(1)).handle(displayMessage);
    }
}