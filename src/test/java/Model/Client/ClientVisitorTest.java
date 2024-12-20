package Model.Client;

import Model.Messages.Client.ErrorResponse;
import Model.Messages.Client.LeaveChannelResponse;
import Model.Messages.UI.DisplayError;
import Model.Messages.UI.UIMessage;
import Model.Messages.UI.UpdateChannels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the ClientVisitor class. This test class ensures that the ClientVisitor
 * correctly handles various client message types, such as LeaveChannelResponse and ErrorResponse,
 * and notifies observers as expected.
 */
class ClientVisitorTest {
    private ClientChannelRecord mockChannelRecord;
    private ClientObserver mockObserver;
    private ClientVisitor clientVisitor;

    /**
     * Sets up the test environment before each test.
     * Mocks the ClientChannelRecord and ClientObserver, and initializes a ClientVisitor with the mocks.
     */
    @BeforeEach
    void setUp() {
        // Mock the channel record and the observer
        mockChannelRecord = mock(ClientChannelRecord.class);
        mockObserver = mock(ClientObserver.class);

        // Create the ClientVisitor with mocked dependencies
        clientVisitor = new ClientVisitor(mockChannelRecord, Collections.singletonList(mockObserver));
    }

    /**
     * Tests the behavior when handling a LeaveChannelResponse.
     * Verifies that the channel is removed from the channel record and that
     * the observer is notified with the updated channels list.
     */
    @Test
    void HandleLeaveChannelResponse() {
        // Given a LeaveChannelResponse
        LeaveChannelResponse message = new LeaveChannelResponse("testChannel");

        // When handle is called
        clientVisitor.handle(message);

        // Verify that the channel was removed from the channel record
        verify(mockChannelRecord).removeChannel("testChannel");

        // Capture the notification sent to the observer
        ArgumentCaptor<UIMessage> captor = ArgumentCaptor.forClass(UIMessage.class);
        verify(mockObserver).update(captor.capture());

        // Assert that the observer was notified with the correct UpdateChannels message
        UpdateChannels updateMessage = (UpdateChannels) captor.getValue();
        assertFalse(updateMessage.getChannels().contains("testChannel"));
    }

    /**
     * Tests the behavior when handling an ErrorResponse.
     * Verifies that the observer is notified with the correct error message.
     */
    @Test
    void HandleErrorResponse() {
        // Given an ErrorResponse
        ErrorResponse message = new ErrorResponse("Some error occurred");

        // When handle is called
        clientVisitor.handle(message);

        // Capture the notification sent to the observer
        ArgumentCaptor<UIMessage> captor = ArgumentCaptor.forClass(UIMessage.class);
        verify(mockObserver).update(captor.capture());

        // Assert that the observer was notified with the correct DisplayError
        DisplayError displayError = (DisplayError) captor.getValue();
        assertEquals("Some error occurred", displayError.getErrorMessage());
    }
}

