package Model.Server;

// Stub Server class for frontend compatibility
public class Server {
    
    public static Server createServerInstance(int port) {
        System.out.println("This is a stub - actual server is now the item-service microservice on port " + port);
        return new Server();
    }
    
    public void startListening() {
        System.out.println("Stub server: startListening() - redirected to microservices");
    }
    
    public void stop() {
        System.out.println("Stub server: stop() - redirected to microservices");
    }
    
    public static void main(String[] args) {
        System.out.println("This is a stub - actual server is now the item-service microservice");
    }
}