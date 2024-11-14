package backend;
import java.net.*;
import java.io.*;

class ClientHandler extends Thread {
    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Server server;
    private ChatChannel currentChannel;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.clientSocket = socket;
        try {
            this.input = new ObjectInputStream(clientSocket.getInputStream());
            this.output = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error setting up input/output stream: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            Message message;
            while ((message = (Message) input.readObject()) != null) {

                sendMessage(message); // just for testing
                // Måster Fixas, Går ej igenom channelNull atm, Och kollar bara /join

                if (message.getContent().startsWith("/join ")) {
                    String channelName = message.getContent().substring(6);
                    joinChannel(channelName);
                } else if (currentChannel != null) {
                    currentChannel.broadcast(message, this);
                }
            }
            System.out.println("Client disconnected.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        } finally {
            try {
                if (currentChannel != null) {
                    currentChannel.removeClient(this);
                }
                clientSocket.close();
                input.close();
                output.close();
            } catch (IOException e) {
                System.out.println("Error closing client connection: " + e.getMessage());
            }
        }
    }

    private void joinChannel(String channelName) {
        if (currentChannel != null) {
            currentChannel.removeClient(this);
        }
        currentChannel = server.getOrCreateChannel(channelName);
        currentChannel.addClient(this);
        sendMessage(new Message("Joined channel: " + channelName, "Server"));
    }

    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}