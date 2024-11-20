package backend;

public class SendMessageCommand implements Command {
    private Message message;
    private ClientHandler clientHandler;

    public SendMessageCommand(Message message, ClientHandler clientHandler) {
        this.message = message;
        this.clientHandler = clientHandler;
    }
    @Override
    public void execute() {
        ChatChannel channel = clientHandler.getCurrentChannel();
        if(channel != null) {
            channel.broadcast(message, clientHandler);
        }
        else {
            clientHandler.sendMessage(new Message("You are not in a channel! Join a channel to send messages!", "Server"));
        }
    }
}
