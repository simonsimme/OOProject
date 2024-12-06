package Main;

public class ChatLauncher {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide an argument: /server or /client");
            return;
        }

        String mode = args[0].toLowerCase();

        switch (mode) {
            case "/server":
                launchServer();
                break;
            case "/client":
                launchClient();
                break;
            default:
                System.out.println("Invalid argument. Use /server to launch the server or /client to launch a client.");
        }
    }

    private static void launchServer() {
        System.out.println("Launching server...");
        ServerApplication.main(new String[]{});
    }

    private static void launchClient() {
        System.out.println("Launching client...");
        ClientApplication.main(new String[]{});
    }
}