package Model.Server.saving;

import Model.Messages.Message;
import Model.Server.ChatChannel;

import java.io.*;

/**
 * The {@code ChatSaver} class is responsible for saving chat messages to a file and retrieving chat history.
 * It handles the persistence of messages for a specific chat channel, storing them in a log file.
 */
public class ChatSaver {
    private static String filePath;
    private BufferedWriter writer;

    /**
     * Constructs a {@code ChatSaver} for the specified channel. This constructor initializes the file path
     * and prepares the log file for writing chat messages.
     *
     * @param channel the {@code ChatChannel} whose messages will be saved
     */
    public ChatSaver(ChatChannel channel) {
        //dont remove the dot in the start
        this("./src/main/java/Model/Server/saving/logs", channel);
    }

    /**
     * Private constructor that sets the file path for saving chat messages and initializes the writer.
     * It also ensures that the folder for logs exists, and writes a header to the log file indicating
     * the channel's name.
     *
     * @param folderPath the folder path where logs will be stored
     * @param channel the {@code ChatChannel} whose messages will be saved
     */
    private ChatSaver(String folderPath, ChatChannel channel) {
        this.filePath = folderPath + File.separator + channel.getName() + ".txt";
        String startString = "/*******Chat history for channel: " + channel.getName() + "********/" + System.lineSeparator();
        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            this.writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(startString);
        } catch (IOException e) {
            System.err.println("Error initializing writer: " + e.getMessage());
        }
    }

    /**
     * Saves a chat message to the log file associated with the channel.
     *
     * @param message the {@code Message} to be saved
     */
    public void saveMessage(Message message) {
        try {
            writer.write(message.toString());

            writer.newLine();

            writer.flush();

        } catch (IOException e) {
            System.err.println("Error writing message to file: " + e.getMessage());
        }
    }

    /**
     * Reads the content of the log file and returns it as a {@link StringBuilder}.
     * This method is static so that the chat history can be retrieved without creating an instance
     * of the {@code ChatSaver} class.
     *
     * @return a {@code StringBuilder} containing the chat history
     */
    private static StringBuilder readFile() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return content;
    }

    /**
     * Retrieves the chat history for a specific channel.
     * This method constructs the file path based on the channel's name and returns its content.
     *
     * @param channel the {@code ChatChannel} whose chat history is to be retrieved
     * @return a {@code StringBuilder} containing the chat history
     */
    public static StringBuilder getChatHistory(ChatChannel channel) {
        try {
            filePath = "./src/main/java/Model/Server/saving/logs" + File.separator + channel.getName() + ".txt";
            return readFile();
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
    }

    /**
     * Clears the content of the log file, essentially deleting all saved chat history.
     */
    public void clearContent(){
        try (PrintWriter pw = new PrintWriter(filePath)){
            pw.print("");
        } catch (FileNotFoundException e) {
            System.out.println("Error clearing file: " + e.getMessage());
        }

    }

    /**
     * Closes the writer used for saving chat messages. It is important to call this method
     * to release resources after completing the writing operations.
     */
    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing writer: " + e.getMessage());
        }
    }
}