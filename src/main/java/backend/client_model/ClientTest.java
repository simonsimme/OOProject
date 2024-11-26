package backend.client_model;

import java.io.IOException;
import java.util.Scanner;

public class ClientTest {
    /*
    public static void main(String[] args) throws IOException {
        // Start the server in a separate thread
        Thread serverThread = new Thread(() -> {
            backend.Server.Server server = backend.Server.Server.createServerInstance(1234);
        });
        serverThread.start();

        // Wait a moment to ensure the server is up and running
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create and run the client
        Client client = new Client("localhost", 1234);
        new Thread(client).start();
        Scanner scanner = new Scanner(System.in);
        // Send a test message
        while (true) {
            System.out.println("Enter a message to send to the server:");
            String message = scanner.nextLine();
            client.sendMessage(message);
        }

    }

     */
}