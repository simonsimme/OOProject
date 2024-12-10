package Model.Client;

import java.io.*;

public class ChannelLogger {
    private String filePath;
    private BufferedWriter writer;


    public ChannelLogger(String channelName) {
        this("src" + File.separator + "main" + File.separator + "java" + File.separator + "backend" + File.separator + "Server" + File.separator + "saving" + File.separator + "logs", channelName);
    }

    private ChannelLogger(String folderPath, String channelName) {
        this.filePath = folderPath + File.separator + channelName + ".txt";
        try {
            File channelLogFile = new File(filePath);
            if (!channelLogFile.exists()) {
                channelLogFile.createNewFile();
                System.out.println("Log file created: " + filePath);
            }
            this.writer = new BufferedWriter(new FileWriter(filePath, true));
            System.out.println("log file found & clearing it");
            clearLogFile();
        } catch (IOException e) {
            System.err.println("Error initializing writer: " + e.getMessage());
        }
    }

    public void logMessage(String userName, String message) {
        try {
            writer.write(userName + ": " + message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing message to file: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
                clearLogFile();
            }
        } catch (IOException e) {
            System.err.println("Error closing writer: " + e.getMessage());
        }
    }
    public StringBuilder loadMessages() {
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

    private void clearLogFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error clearing log file: " + e.getMessage());
        }
    }

}