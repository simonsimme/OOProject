package Model.NewMessage;



import Model.Messages.Message;
import Model.Server.ChatChannel;
import Model.Server.Server;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class TestClientHandler {

    Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final Server1 server;

    TestClientHandler(Socket socket, Server1 server){
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        try {
            while (input != null) {
                Sendabel message = this.readMessage();
                if (message != null) {
                    processMessage(message);
                }
            }
        } catch (IllegalArgumentException | IOException | ClassNotFoundException e) {}

    }
    Sendabel readMessage() throws IOException, ClassNotFoundException {
        return (Sendabel) input.readObject();
    }
    void processMessage(Sendabel message){
        if(message.getType() instanceof TextMessage){

        }
        else{

        }


    };
    public void sendMessage(Sendabel message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
           // System.out.println("Error sending message: " + e.getMessage());
        }
    }

}
