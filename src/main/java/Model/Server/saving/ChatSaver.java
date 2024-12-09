package Model.Server.saving;
import Model.Server.ChatChannel;

import java.io.*;

public class ChatSaver {
    private  String filePath;
    //private final String folderPath;
    private BufferedWriter writer;

    //private final String pathToLoger = "./src/main/java/Model/Server/saving/logs";

    public ChatSaver(ChatChannel channel) {
        //dont remove the dot in the start
        this("./src/main/java/Model/Server/saving/logs" , channel);
    }

    private ChatSaver(String folderPath, ChatChannel channel) {
        this.filePath = folderPath + File.separator + channel.getName() + ".txt";
        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            this.writer = new BufferedWriter(new FileWriter(filePath, true));
        } catch (IOException e) {
            System.err.println("Error initializing writer: " + e.getMessage());
        }
    }

    public void update(String userName, String channelName, String message) {
        saveMessage(userName, channelName, message);
    }
    public void saveMessage(String userName, String channelName, String message) {
        try {
            writer.write(channelName);
            writer.newLine();

            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing message to file: " + e.getMessage());
        }
    }
    private StringBuilder readFile() {
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
    public StringBuilder getChatHistory(ChatChannel channel) {
        try {
            this.filePath = "./src/main/java/Model/Server/saving/logs" + File.separator + channel.getName() + ".txt";
            return readFile();
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
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