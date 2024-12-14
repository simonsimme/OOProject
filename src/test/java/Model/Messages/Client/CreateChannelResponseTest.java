package Model.Messages.Client;

import Model.Client.ClientVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link CreateChannelResponse} class
 * Tests the functionality of the methods in the {@code JoinChannelCommand} class
 */
class CreateChannelResponseTest {
    private String channelName;
    private CreateChannelResponse reponse;

    /**
     * Sets up the test data and initializes a {@code CreateChannelResponse} instance.
     */
    @BeforeEach
    void setUp() {
        this.channelName = "channelName";
        this.reponse = new CreateChannelResponse(channelName);
    }

    /**
     * Test the {@code getChannelName} method to ensure it returns the correct channelName.
     */
    @Test
    void getChannelName() {
        String result = reponse.getChannelName();
        assertEquals(channelName, result);
    }

    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code ServerMessageVisitor}.
     */
    @Test
    void accept() {
        //Arrange: Create a mock ClientVisitor
        ClientVisitor mockVisitor = mock(ClientVisitor.class);
        //Act: Call accept on the response with the mock Visitor
        reponse.accept(mockVisitor);
        //Assert: Verify the handle method is called with the correct response
        verify(mockVisitor, times(1)).handle(reponse);
    }
}