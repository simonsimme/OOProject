package Model.Messages.UI;

import View.components.Decoraters.HandleMessageDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link UpdateChannels} class
 * Tests the functionality of the methods in the {@code UpdateChannels} class
 */
class UpdateChannelsTest {
    private String current;
    private UpdateChannels uc;

    /**
     * Sets up the test data and initializes a {@code UpdateChannels} instance.
     */
    @BeforeEach
    void setUp() {
        List<String> channels = List.of("General", "TechTalk", "Random", "HelpDesk", "News");
        this.current = "TechTalk";
        this.uc = new UpdateChannels(channels, current);
    }

    /**
     * Test the {@code getChannels} method to ensure it returns the correct channels.
     * Correct size and correct expected names.
     */
    @Test
    void getChannels() {
        assertEquals(5, uc.getChannels().size());
        assertTrue(uc.getChannels().contains("General"), "The channels list should contain 'General'.");
        assertTrue(uc.getChannels().contains("TechTalk"), "The channels list should contain 'TechTalk'.");
        assertTrue(uc.getChannels().contains("Random"), "The channels list should contain 'Random'.");
        assertTrue(uc.getChannels().contains("HelpDesk"), "The channels list should contain 'HelpDesk'.");
        assertTrue(uc.getChannels().contains("News"), "The channels list should contain 'News'.");
    }

    /**
     * Test the {@code getCurrentChannel} method to ensure it returns the correct channel name.
     */
    @Test
    void getCurrentChannel() {
        String result = uc.getCurrentChannel();
        assertEquals(current, result);
    }

    /**
     * Tests the {@code accept} method to ensure it calls the {@code handle} method on the given {@code HandleMessageDecorator}.
     */
    @Test
    void accept() {
        //Arrange: Create a mock ClientVisitor
        HandleMessageDecorator mockVisitor = mock(HandleMessageDecorator.class);
        //Act: Call accept on the response with the mock Visitor
        uc.accept(mockVisitor);
        //Assert: Verify the handle method is called with the correct response
        verify(mockVisitor, times(1)).handle(uc);
    }
}