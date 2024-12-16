package Model.Server.saving;

import Model.Messages.Message;
import Model.Messages.Server.SendMessageInChannelCommand;
import Model.Server.ChatChannel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ChatSaver} class.
 * This test class ensures the functionality of saving messages,
 * retrieving chat history, and managing the lifecycle of the ChatSaver.
 */
class ChatSaverTest {
    private ChatSaver chatSaver;
    private ChatChannel channel;
    private Message message;
    private final String filePath = "./src/main/java/Model/Server/saving/logs/TestChannel.txt";

    /**
     * Sets up the test environment by initializing the chat channel, message,
     * and {@link ChatSaver} instance. Ensures a clean state for each test.
     *
     * @throws IOException if file operations fail.
     */
    @BeforeEach
    void setUp() throws IOException {
        this.channel = new ChatChannel("TestChannel", "Password");
        this.message = new SendMessageInChannelCommand("user", "TestChannel", "This is the message being sent", false);
        Files.createDirectories(Paths.get("./src/main/java/Model/Server/saving/logs"));

        File testFile = new File(filePath);
        if (testFile.exists()) {
            testFile.delete();
        }
        chatSaver = new ChatSaver(channel);
    }

    /**
     * Cleans up the test environment by deleting test files.
     * Ensures no residual files remain after tests.
     *
     * @throws IOException if file deletion fails.
     */
    @AfterEach
    void tearDown() throws IOException {
        File testFile = new File(filePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    /**
     * Tests the {@link ChatSaver#saveMessage(String, Message)} method.
     * Verifies that a message is correctly saved to a file.
     *
     * @throws IOException if file reading or writing fails.
     */
    @Test
    void saveMessage() throws IOException {
        // Save a message
        chatSaver.saveMessage("TestChannel", message);

        // Check if the message was written to the file
        File testFile = new File(filePath);
        assertTrue(testFile.exists());

        // Read the content of the file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }

        // Validate that the file contains the saved message
        assertTrue(content.toString().contains(message.toString()));
    }

    /**
     * Tests the {@link ChatSaver#getChatHistory(ChatChannel)} method.
     * Ensures that the chat history is correctly retrieved.
     */
    @Test
    void getChatHistory() {
        chatSaver.saveMessage("TestChannel", message);
        StringBuilder chatHistory = ChatSaver.getChatHistory(channel);
        assertNotNull(chatHistory);
        assertTrue(chatHistory.toString().contains("Test message in chat history"));
    }

    /**
     * Tests the {@link ChatSaver#close()} method.
     * Verifies that closing the ChatSaver prevents further operations and produces the expected error.
     */
    @Test
    void close() {
        // Initialize and save a message
        chatSaver.saveMessage("TestChannel", message);

        // Close the writer
        chatSaver.close();

        // Redirect System.err to capture the error message
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        // Attempt to save another message
        chatSaver.saveMessage("TestChannel", new SendMessageInChannelCommand("user", "TestChannel", "Another message", false));

        // Restore the original System.err
        System.setErr(originalErr);

        // Verify the error message
        String errorMessage = errContent.toString();
        assertTrue(errorMessage.contains("Error writing message to file: Stream closed"));
    }
}