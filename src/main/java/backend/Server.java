package backend;

import java.net.*;
import java.io.*;


public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private static Server thisServer = null;
    private final ServerState ss = ServerState.createServerStateInstance();

    public static Server createServerInstance(int port){
        if (thisServer == null){
            return new Server(port);
        }
        else{
            return thisServer;
        }
    }
    private Server(int port)  {
        try {
                this.server = new ServerSocket(port);
        while (true) {
                System.out.println("Waiting for clients...");
                // Accept a new client connection
                Socket clientSocket = server.accept();

                System.out.println("New client connected" + clientSocket.getLocalAddress());

                // Handle the new client in a separate thread
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandler.start();
                ss.add(clientHandler);
        }}
            catch (IOException e){

                System.out.println(e.getMessage());
            }
    }
    public static void main(String args[])
    {
        Server server = Server.createServerInstance(8070);
    }

    // Method to broadcast message to all connected clients except the sender
    public synchronized void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : ss.clients) {
            if (client != sender) { // Send message to all clients except the sender
                client.sendMessage("from " + sender + ":" + message);
            }
        }
    }
}
