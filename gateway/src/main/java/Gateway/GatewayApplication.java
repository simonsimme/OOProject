package Gateway;

import Model.Messages.Server.ServerMessage;
import Model.Messages.Client.ClientMessage;

/**
 * API Gateway for the DevOps Microservices Chat Application.
 * Routes requests between frontend and backend services.
 */
public class GatewayApplication {
    
    private static final int GATEWAY_PORT = 8080;
    private static final String USER_SERVICE_URL = "http://localhost:8081";
    private static final String ITEM_SERVICE_URL = "http://localhost:8082";
    
    public static void main(String[] args) {
        System.out.println("Starting API Gateway on port " + GATEWAY_PORT);
        
        GatewayApplication gateway = new GatewayApplication();
        gateway.start();
    }
    
    public void start() {
        // Initialize gateway server
        // Setup routing rules
        // Handle requests and route to appropriate services
        System.out.println("Gateway is running and ready to route requests");
        System.out.println("User Service: " + USER_SERVICE_URL);
        System.out.println("Item Service: " + ITEM_SERVICE_URL);
    }
    
    /**
     * Route user-related requests to user service
     */
    public void routeToUserService(Object request) {
        // Route to user service
        System.out.println("Routing to User Service: " + request);
    }
    
    /**
     * Route chat/channel-related requests to item service
     */
    public void routeToItemService(Object request) {
        // Route to item service
        System.out.println("Routing to Item Service: " + request);
    }
}