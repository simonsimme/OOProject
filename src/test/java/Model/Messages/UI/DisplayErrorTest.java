package Model.Messages.UI;

import View.components.Decoraters.HandleMessageDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link DisplayError} class
 * Tests the functionality of the methods in the {@code DisplayError} class
 */

class DisplayErrorTest {
    private String errorMsg;
    private DisplayError displayError;

    /**
     * Sets up the test data and initializes a {@code DisplayError} instance.
     */
    @BeforeEach
    void setUp() {
        this.errorMsg = "Error message";
        this.displayError = new DisplayError(errorMsg);
    }

    /**
     * Test the {@code getErrorMessage} method to ensure it returns the correct error message.
     */
    @Test
    void getErrorMessage() {
        String result = displayError.getErrorMessage();
        assertEquals(errorMsg, result);
    }

    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code HandleMessageDecorator}.
     */
    @Test
    void accept() {
        //Arrange: Create a mock ClientVisitor
        HandleMessageDecorator mockVisitor = mock(HandleMessageDecorator.class);
        //Act: Call accept on the response with the mock Visitor
        displayError.accept(mockVisitor);
        //Assert: Verify the handle method is called with the correct response
        verify(mockVisitor, times(1)).handle(displayError);
    }
}