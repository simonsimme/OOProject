package backend;
import java.net.*;
import java.io.*;

class ClientHandler extends Thread {
    private Socket clientSocket;
    private DataInputStream input;
    private Server server;
    private PrintWriter output;


    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.clientSocket = socket;
        try {
            this.input = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error setting up input stream: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String message = "";
        try {
            // Keep reading messages until the client sends "Over"
            while (!message.equals("Over")) {
                message = input.readUTF();
                System.out.println("Received from client: " + message);
                server.broadcast(message, this);
            }

            System.out.println("Client disconnected.");

        } catch (IOException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        } finally {
            try {
                // Close resources for this client
                clientSocket.close();
                input.close();
            } catch (IOException e) {
                System.out.println("Error closing client connection: " + e.getMessage());
            }
        }
    }
    public void sendMessage(String m1) {
            output.println(m1);
            output.flush();
    }
}
