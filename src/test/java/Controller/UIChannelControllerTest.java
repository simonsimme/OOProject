package Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import Model.Client.Client;
import View.components.IView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

/**
 * Unit tests for the {@link UIChannelController} class.
 * These tests verify the correct behavior of the channel-related methods.
 */
class UIChannelControllerTest {
    @Mock
    private IView view;
    @Mock
    private Client client;
    private UIChannelController controller;

    /**
     * Sets up the test environment by initializing the mocks and the controller.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new UIChannelController(view, client);
    }

    /**
     * Tests the {@link UIChannelController#joinChannel()} method with valid input.
     */
    @Test
    void testJoinChannel_validInput() {
        // Arrange
        String[] channelNameAndPassword = {"testChannel", "testPassword"};
        when(view.getChannelNameAndPasswordInput("Join")).thenReturn(channelNameAndPassword);

        // Act
        controller.joinChannel();

        // Assert
        verify(client).joinChannel("testChannel", "testPassword");
    }

    /**
     * Tests the {@link UIChannelController#joinChannel()} method when the channel name is empty.
     */
    @Test
    void testJoinChannel_emptyChannelName() {
        // Arrange
        String[] channelNameAndPassword = {"", "testPassword"};
        when(view.getChannelNameAndPasswordInput("Join")).thenReturn(channelNameAndPassword);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.joinChannel();
        });
        assertEquals("Channel name cannot be empty", exception.getMessage());
    }

    /**
     * Tests the {@link UIChannelController#joinChannel()} method when the input is cancelled.
     */
    @Test
    void testJoinChannel_cancelledInput() {
        // Arrange
        when(view.getChannelNameAndPasswordInput("Join")).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.joinChannel();
        });
        assertEquals("STOPPED", exception.getMessage());
    }

    /**
     * Tests the {@link UIChannelController#createChannel()} method with valid input.
     */
    @Test
    void testCreateChannel_validInput() {
        // Arrange
        String[] channelNameAndPassword = {"newChannel", "newPassword"};
        when(view.getChannelNameAndPasswordInput("Create")).thenReturn(channelNameAndPassword);

        // Act
        controller.createChannel();

        // Assert
        verify(client).createChannel("newChannel", "newPassword");
    }

    /**
     * Tests the {@link UIChannelController#createChannel()} method when the channel name is empty.
     */
    @Test
    void testCreateChannel_emptyChannelName() {
        // Arrange
        String[] channelNameAndPassword = {"", "newPassword"};
        when(view.getChannelNameAndPasswordInput("Create")).thenReturn(channelNameAndPassword);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.createChannel();
        });
        assertEquals("Channel name cannot be empty", exception.getMessage());
    }

    /**
     * Tests the {@link UIChannelController#createChannel()} method when the input is cancelled.
     */
    @Test
    void testCreateChannel_cancelledInput() {
        // Arrange
        when(view.getChannelNameAndPasswordInput("Create")).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.createChannel();
        });
        assertEquals("STOPPED", exception.getMessage());
    }

    /**
     * Tests the setNickName() method.
     */
    @Test
    void testSetNickName() {
        // Arrange
        String nickname = "testUser";

        // Act
        controller.setNickName(nickname);

        // Assert
        verify(client).setNickName(nickname);
        verify(view).showNotification("Your nickname is now: " + nickname);
    }
}
