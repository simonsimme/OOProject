package Model.Client;

// Stub Client class for frontend compatibility
public class Client {
    private String serverHost;
    private int serverPort;
    private String userName = "default-user";
    
    public Client(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }
    
    public void createChannel(String name, String password) {
        System.out.println("Frontend: Creating channel via gateway: " + name);
    }
    
    public void joinChannel(String name, String password) {
        System.out.println("Frontend: Joining channel via gateway: " + name);
    }
    
    public void switchChannel(String name) {
        System.out.println("Frontend: Switching channel via gateway: " + name);
    }
    
    public void switchChannel() {
        System.out.println("Frontend: Switching to next channel via gateway");
    }
    
    public void sendMessage(String message) {
        System.out.println("Frontend: Sending message via gateway: " + message);
    }
    
    public void leaveChannel() {
        System.out.println("Frontend: Leaving channel via gateway");
    }
    
    public String getCurrentChannelName() {
        return "default-channel";
    }
    
    public void attach(Object observer) {
        System.out.println("Frontend: Attaching observer");
    }
    
    public void setNickName(String nickName) {
        this.userName = nickName;
        System.out.println("Frontend: Setting nickname: " + nickName);
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void disconnect() {
        System.out.println("Frontend: Disconnecting via gateway");
    }
}