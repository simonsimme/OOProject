package backend.toLater;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream serverInput = null;

    public Client(String address, int port)  {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            out = new ObjectOutputStream(socket.getOutputStream());
            serverInput = new ObjectInputStream(socket.getInputStream());

            new Thread(new Runnable() {
                public void run() {
                    try {
                        Message serverMessage;
                        while ((serverMessage = (Message) serverInput.readObject()) != null) {
                            System.out.println("Server: " + serverMessage);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error reading from server: " + e.getMessage());
                    }
                }
            }).start();
        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (!line.equals("Over")) {
            try {
                line = input.readLine();
                Message message = new Message(line, "Client");
                out.writeObject(message);
                out.flush();
            } catch (IOException i) {
                System.out.println(i);
            }
        }

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 8070);
    }
}