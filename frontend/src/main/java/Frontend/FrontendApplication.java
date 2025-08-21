package Frontend;

import Controller.UIController;
import View.components.Factorys.StandardViewFactory;
import View.components.Factorys.ViewFactory;
import View.components.IView;

/**
 * Frontend Application
 * Provides the user interface for the chat application
 */
public class FrontendApplication {
    
    private static final String GATEWAY_URL = "http://localhost:8080";
    
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Frontend Application");
        System.out.println("Connecting to Gateway: " + GATEWAY_URL);
        
        FrontendApplication frontend = new FrontendApplication();
        frontend.start();
    }
    
    public void start() throws Exception {
        // Initialize frontend UI
        // Connect to gateway
        // Setup UI controllers
        System.out.println("Frontend Application is running");
        
        // Create a basic UI setup (simplified from original ChatApplication)
        ViewFactory viewFactory = new StandardViewFactory();
        IView view = viewFactory.createView();
        
        // Note: This is a simplified setup. In a real microservices architecture,
        // the frontend would communicate with the gateway via HTTP/REST calls
        // instead of direct socket connections to services.
        
        System.out.println("Frontend UI initialized");
        System.out.println("Ready to connect users to chat services via Gateway");
    }
    
    /**
     * Connect to gateway for backend communication
     */
    public void connectToGateway() {
        // Gateway connection logic
        System.out.println("Connecting to API Gateway at " + GATEWAY_URL);
    }
}