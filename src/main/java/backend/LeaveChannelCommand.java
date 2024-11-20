package backend;

public class LeaveChannelCommand implements Command {
    private ClientHandler clienterHandler;

    public LeaveChannelCommand(ClientHandler clientHandler) {
        this.clienterHandler = clientHandler;
    }
    @Override
    public void execute() {
        clienterHandler.leaveChannel();
    }
}
