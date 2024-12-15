package Model.Messages.UI;

import View.components.Decoraters.HandleMessageDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link UIChannelHistory} class
 * Tests the functionality of the methods in the {@code UIChannelHistory} class
 */
class UIChannelHistoryTest {
    private String history;
    private UIChannelHistory UIch;

    /**
     * Sets up the test data and initializes a {@code UIChannelHistory} instance.
     */
    @BeforeEach
    void setUp() {
        this.history = """
            /*******Chat history for channel: ch********/
            2024-12-15T11:49:25.606782400 Message: rHA7i8PEqJX15Rt6T/FTFw== from Guest2
            2024-12-15T11:49:28.080430600 Message: MSHHHlpcXD5b4LaG2BVWOg== from Guest2
            2024-12-15T11:49:29.969011500 Message: ucEBh/2sSOvrtWA53Hif9Q== from Guest2
            2024-12-15T11:49:32.027282600 Message: ucEBh/2sSOvrtWA53Hif9Q== from Guest2
            2024-12-15T11:49:37.165547900 Message: ucEBh/2sSOvrtWA53Hif9Q== from Guest2
            2024-12-15T11:49:39.095792 Message: ucEBh/2sSOvrtWA53Hif9Q== from Guest2
            2024-12-15T11:49:49.954386900 Message: has disconnected from the channel from Guest2
        """;
        this.UIch = new UIChannelHistory(history);
    }

    /**
     * Tests the {@code parseHistory} method to ensure it correctly parses the history string.
     */
    //TODO I am not sure how the textMessages are being parsed. Please update test or the raw data to match the correct format.
    @Test
    void parseHistory() {
        // Act: Parse the history
        List<DisplayMessage> messages = UIch.parseHistory();

        // Assert: Verify the returned list contains the correct data
        assertEquals(7, messages.size(), "There should be 7 messages parsed.");

        // Verify the first message
        DisplayMessage firstMessage = messages.get(0);
        assertEquals("Guest2", firstMessage.getUserName());
        assertEquals("rHA7i8PEqJX15Rt6T/FTFw==", firstMessage.getMessage());
        assertEquals("---", firstMessage.getChannelName());

        // Verify the last message
        DisplayMessage lastMessage = messages.get(6);
        assertEquals("Guest2", lastMessage.getUserName());
        assertEquals("has disconnected from the channel", lastMessage.getMessage());
        assertEquals("---", lastMessage.getChannelName());
    }

    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code HandleMessageDecorator}.
     */
    @Test
    void accept() {
        //Arrange: Create a mock ClientVisitor
        HandleMessageDecorator mockVisitor = mock(HandleMessageDecorator.class);
        //Act: Call accept on the response with the mock Visitor
        UIch.accept(mockVisitor);
        //Assert: Verify the handle method is called with the correct response
        verify(mockVisitor, times(1)).handle(UIch);
    }
}