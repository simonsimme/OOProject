package Model.Messages.Client;

import Model.Client.ClientVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link ErrorResponse} class
 * Tests the functionality of the methods in the {@code JoinChannelCommand} class
 */
class ErrorResponseTest {
    private String errorMsg;
    private ErrorResponse response;

    /**
     * Sets up the test data and initializes a {@code CreateChannelResponse} instance.
     */
    @BeforeEach
    void setUp() {
        this.errorMsg = "Error Message";
        this.response = new ErrorResponse(errorMsg);
    }

    /**
     * Test the {@code getErrorMessage} method to ensure it returns the correct Error Message.
     */
    @Test
    void getErrorMessage() {
        String result = response.getErrorMessage();
        assertEquals(errorMsg, result);
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