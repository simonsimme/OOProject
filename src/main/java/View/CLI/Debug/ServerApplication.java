package View.CLI.Debug;

import backend.Server.Server;

import java.io.IOException;
import java.util.Scanner;

/**
 * Run this to start a server
 */
public class ServerApplication {
    private static final int port = 2461;


    public static void main(String[] args){
        Server server = Server.createServerInstance(port);


        Thread serverThread = new Thread(() -> {
            server.startListening();
        });
        serverThread.start();
        System.out.println("Server started successfully.");
        System.out.println("Type /stop to stop the server and close the socket!");
        System.out.println("If you terminate the process without stopping the \n" +
                "socket, the server will not be able to start again \n" +
                "next run since the socket will be in use.");
        while(true){
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equals("/stop")){
                try {
                    server.stop();
                    System.exit(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
