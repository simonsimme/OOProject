package Model.Messages.Client;

import Model.Client.ClientVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link RetrieveChatHistoryResponse} class
 * Tests the functionality of the methods in the {@code RetrieveChatHistoryResponse} class
 */
class RetrieveChatHistoryResponseTest {
    private String channelName;
    private RetrieveChatHistoryResponse response;

    /**
     * Sets up the test data and initializes a {@code RetrieveChatHistoryResponse} instance.
     */
    @BeforeEach
    void setUp() {
        this.channelName = "channelName";
        StringBuilder history = new StringBuilder("First message\nSecond message\nThird message");
        this.response = new RetrieveChatHistoryResponse(channelName, history);
    }

    /**
     * Test the {@code getChannelName} method to ensure it returns the correct channelName.
     */
    @Test
    void getChannelName() {
        String result = response.getChannelName();
        assertEquals(channelName, result);
    }

    /**
     * Test the {@code toString} method to ensure it returns the correct string representation.
     */
    @Test
    void testToString() {
        String expected = "RetrieveChatHistoryResponse{channelName='channelName', history=First message\nSecond message\nThird message}";
        assertEquals(expected, response.toString(), "The toString method should return the correct string representation.");
    }

    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code ClientVisitor}.
     */
    @Test
    void accept() {
        //Arrange: Create a mock ClientVisitor
        ClientVisitor mockVisitor = mock(ClientVisitor.class);
        //Act: Call accept on the response with the mock Visitor
        response.accept(mockVisitor);
        //Assert: Verify the handle method is called with the correct response
        verify(mockVisitor, times(1)).handle(response);
    }
}