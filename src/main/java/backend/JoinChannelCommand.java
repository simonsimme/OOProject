package backend;

public class JoinChannelCommand implements Command {
    private String channelName;
    private ClientHandler clientHandler;

    public JoinChannelCommand(String channelName, ClientHandler clientHandler) {
        this.channelName = channelName;
        this.clientHandler = clientHandler;
    }
    @Override
    public void execute() {
        clientHandler.joinChannel(channelName);
    }
}
