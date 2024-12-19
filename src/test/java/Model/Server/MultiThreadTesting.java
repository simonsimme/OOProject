package Model.Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiThreadTesting {

    private Server server;
    //The port number used for testing.
    private int port;
    private Thread serverThread;

    /**
     * Sets up the test environment by initializing the server instance
     * and assigning a test port before each test case.
     */
    @BeforeEach
    void setUp() {
        port = 12345;
        server = Server.createServerInstance(port);

        serverThread = new Thread(() -> server.startListening());
        serverThread.setDaemon(true);
        serverThread.start();
    }

    //test to check if the server can habdle multiple user connecting to the server
    @Test
    void HandleMultipleClients(){
        List<Socket> clientSockets = new ArrayList<>();
        List<Boolean> truthTable = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            try {
                Socket clientSocket = new Socket("localhost", 12345);
                clientSockets.add(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Socket clientSocket : clientSockets) {
            truthTable.add(clientSocket.isConnected());
        }
        assertTrue(truthTable.stream().allMatch(x -> x == true));
    }
    @Test
    void testToBreakeTheServer(){
        List<Socket> clientSockets = new ArrayList<>();
        List<Boolean> truthTable = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            try {
                Socket clientSocket = new Socket("localhost", 12345);
                clientSockets.add(clientSocket);
                truthTable.add(clientSocket.isConnected());

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                for (Socket clientSocket : clientSockets) {
                    try {
                        if (clientSocket != null && !clientSocket.isClosed()) {
                            clientSocket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
        }
        assertTrue(truthTable.stream().allMatch(x -> x == true));
    }

    //test to check if the server can handle multiple user connecting to the server and closing the connection
    @Test
    void HandleConnectionAndClosing() throws IOException {
        List<Socket> clientSockets = new ArrayList<>();
        List<Boolean> truthTable = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            try {
                Socket clientSocket = new Socket("localhost", 12345);
                clientSockets.add(clientSocket);
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Socket clientSocket : clientSockets) {
            if(clientSocket.isClosed()){
                truthTable.add(true);
            }
        }
        assertTrue(truthTable.stream().allMatch(x -> x == true));
    }
}
