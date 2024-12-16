package Model.NewMessage;

import Model.EncryptionLayer;
import Model.Server.saving.ChatSaverObserver;
import javax.crypto.SecretKey;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The Server class implements a basic server that listens for incoming client
 * connections, handles chat channels and communication between clients.
 */
public class Server1 {

    private ServerSocket server = null;

    private static Server1 thisServer = null;


    private volatile boolean isRunning;


    public static Server1 createServerInstance(int port) {
        if (thisServer == null) {
            try {

                thisServer = new Server1(port);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return thisServer;
    }

    private Server1(int port) throws IOException {
        isRunning = true;
        this.server = new ServerSocket(port);
    }

    public  void startListening() {
        try {
            while (isRunning) {

                System.out.println("Waiting for clients...");
                Socket clientSocket = server.accept();

                if(!isRunning) {
                    break;
                }
                System.out.println("New client connected: " + clientSocket.getLocalAddress());

                //ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                //clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public synchronized void createChannel(String channelName, String password) {

    }


}