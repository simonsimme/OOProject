package Model.Server.saving;

import Model.Messages.Message;
import Model.Server.ChatChannel;
import java.io.*;

/**
 * The {@code ChatSaver} class is responsible for saving chat messages to a file.
 */
public class ChatSaver {
    private static String filePath;
    private BufferedWriter writer;

    private final String startString;


    public ChatSaver(ChatChannel channel) {
        //dont remove the dot in the start
        this("./src/main/java/Model/Server/saving/logs", channel);
    }

    private ChatSaver(String folderPath, ChatChannel channel) {
        this.filePath = folderPath + File.separator + channel.getName() + ".txt";
        this.startString = "/*******Chat history for channel: " + channel.getName() + "********/" + System.lineSeparator();
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


    public void saveMessage(String channelName, Message message) {
        try {
            writer.write(message.toString());

            writer.newLine();

            writer.flush();

        } catch (IOException e) {
            System.err.println("Error writing message to file: " + e.getMessage());
        }
    }

    /**
     * Reads the content of the file and returns it as a {@link StringBuilder}.
     * This is statick method to be able to read the history of the channel without creating an instance of the class.
     *
     * @return
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

    //extract the history of the channel
    public static StringBuilder getChatHistory(ChatChannel channel) {
        try {
            filePath = "./src/main/java/Model/Server/saving/logs" + File.separator + channel.getName() + ".txt";
            return readFile();
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
    }
    public void clearContent(){
        try (PrintWriter pw = new PrintWriter(filePath)){
            pw.print("");
        } catch (FileNotFoundException e) {
            System.out.println("Error clearing file: " + e.getMessage());
        }

    }

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