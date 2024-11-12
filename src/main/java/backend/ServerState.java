package backend;

import java.util.ArrayList;

public class ServerState {

    private static ServerState serverState;
    //Array of threads
    ArrayList<ClientHandler> clients;

    public static ServerState createServerStateInstance(){
        if (serverState == null){
            return new ServerState();
        }
        else {
            return serverState;
        }
    }
    private ServerState() {
        this.clients = new ArrayList<>();
    }
    public void add(ClientHandler ch) {
        if(!clients.contains(ch)) {
            clients.add(ch);
        }
    }
    public void remove(ClientHandler ch) {
        if(!clients.isEmpty()) {
                clients.remove(ch);
        }
    }
}
