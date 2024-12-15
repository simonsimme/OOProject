package Model.Messages.Client;

import Model.Client.ClientVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link JoinChannelResponse} class
 * Tests the functionality of the methods in the {@code JoinChannelCommand} class
 */
class JoinChannelResponseTest {
    private String channelName;
    private JoinChannelResponse response;

    /**
     * Sets up the test data and initializes a {@code JoinChannelResponse} instance.
     */
    @BeforeEach
    void setUp() {
        this.channelName = "channelName";
        this.response = new JoinChannelResponse(channelName);
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