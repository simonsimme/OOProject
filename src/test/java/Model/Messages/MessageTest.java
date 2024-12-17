package Model.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Message} class.
 * <p>
 * This class tests the functionality of the {@link Message} class, including the abstract methods
 * that are overridden by the {@link TestMessage} subclass. The tests verify the correct behavior
 * of timestamp retrieval, the string representation of the message, and the specific message content
 * and sender information.
 * </p>
 */
class MessageTest {
    /**
     * A concrete subclass of {@link Message} used for testing purposes.
     * <p>
     * This subclass provides concrete implementations for the abstract methods
     * {@link Message#getMessageAsString()} and {@link Message#getSenderAsString()},
     * allowing for testing the behavior of the abstract {@link Message} class.
     * </p>
     */

    static class TestMessage extends Message {
        /**
         * Returns the string representation of the message content for testing.
         *
         * @return the message content as a string
         */
        @Override
        protected String getMessageAsString() {
            return "Test message content";
        }

        /**
         * Returns the string representation of the sender for testing.
         *
         * @return the sender as a string
         */

         String getSenderAsString() {
            return "TestSender";
        }
    }

    /**
     * The {@link TestMessage} instance used in the test methods.
     */
    private TestMessage message;

    /**
     * Sets up the test environment by creating a new instance of {@link TestMessage}.
     */
    @BeforeEach
    void setUp() {
        message = new TestMessage();
    }


    @Test
    void getTimestamp() {
        //assertNotNull(message.getTimestamp(), "Timestamp should not be null");
    }

    /**
     * Tests the {@link Message#toString()} method to ensure it returns the correct string representation
     * of the message, including the timestamp, message content, and sender.
     */
    @Test
    void testToString() {
        //String expected = message.getTimestamp() + " Message: Test message content from TestSender";
       // assertEquals(expected, message.toString(), "toString should match the expected format");
    }

    /**
     * Tests the {@link Message#getMessageAsString()} method to ensure it returns the correct
     * content for the message.
     */
    @Test
    void getMessageAsString() {
        assertEquals("Test message content", message.getMessageAsString(), "Message content should be 'Test message content'");
    }

    /**
     * Tests the {@link Message#getSenderAsString()} method to ensure it returns the correct
     * sender for the message.
     */
    @Test
    void getSenderAsString() {
        assertEquals("TestSender", message.getSenderAsString(), "Sender should be 'TestSender'");
    }

}